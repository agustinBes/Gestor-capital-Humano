package com.practical.consultora_rrhh.services;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.practical.consultora_rrhh.modelo.clases.Candidato;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import org.openpdf.pdf.ITextRenderer;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


/**
 * Convierte las listas del CU028 en un PDF: Thymeleaf arma el HTML (template/orden-merito-pdf.html)
 * y openpdf-html (CSS 2.1) lo transforma en bytes.
 */
@Component
public class OrdenMeritoPdfRenderer {

    // ---- Modelos de vista (todo ya formateado como String para el template) -------------
    public record Minimo(String nombre, String valor) {}
    public record Seg(String ancho, String color) {}
    public record CompFila(String nombre, String obtenido, String minimo, boolean cumple, List<Seg> barra) {}
    public record FilaMerito(String pos, String colorPos, String nombre, String doc, String puntaje,
                             List<CompFila> comps, List<String> fallas,
                             String inicio, String fin, String accesos) {}
    public record FilaSimple(String doc, String nombre, String inicio, String ultimo, String accesos) {}

    private static final Locale AR = Locale.forLanguageTag("es-AR");
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    // Índices: 0 verde, 1 marca del mínimo, 2 rojo, 3 gris
    private static final String[] COLORES = { "#12a36b", "#16213a", "#d64545", "#eceff5" };
    private static final String[] COLOR_POS = { "#e8b23a", "#b9c2d2", "#c98550" }; // oro, plata, bronce

    private final TemplateEngine templateEngine;

    public OrdenMeritoPdfRenderer(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    // ---- API ------------------------------------------------------------------------------

    public byte[] renderizar(Puesto puesto,
                             List<PuestoCompetencia> requeridas,
                             List<Cuestionario> aprobados,
                             List<Cuestionario> noAprobados,
                             List<Cuestionario> incompletos,
                             List<Cuestionario> sinContestar) {

        List<FilaMerito> filasAprobados = new ArrayList<>();
        for (int i = 0; i < aprobados.size(); i++) {
            filasAprobados.add(filaMerito(aprobados.get(i), i + 1, requeridas));
        }
        List<FilaMerito> filasNoAprobados = noAprobados.stream()
                .map(c -> filaMerito(c, null, requeridas)).toList();

        Context ctx = new Context(AR);
        ctx.setVariable("puestoNombre", puesto.getNombre());
        ctx.setVariable("puestoCodigo", puesto.getCodigo());
        ctx.setVariable("puestoEmpresa", puesto.getEmpresa());
        ctx.setVariable("minimos", requeridas.stream()
                .map(pc -> new Minimo(pc.getCompetencia().getNombre(), num(BigDecimal.valueOf(pc.getPonderacion()))))
                .toList());
        ctx.setVariable("aprobados", filasAprobados);
        ctx.setVariable("noAprobados", filasNoAprobados);
        ctx.setVariable("incompletos", incompletos.stream().map(this::filaSimple).toList());
        ctx.setVariable("sinContestar", sinContestar.stream().map(this::filaSimple).toList());
        ctx.setVariable("generado", FECHA.format(LocalDateTime.now()));

        String html = templateEngine.process("orden-merito-pdf", ctx);
        return htmlAPdf(html);
    }

    private byte[] htmlAPdf(String html) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo generar el PDF del orden de mérito", e);
        }
    }

    // ---- Armado de filas -----------------------------------------------------------------

    private FilaMerito filaMerito(Cuestionario c, Integer pos, List<PuestoCompetencia> requeridas) {
        List<CompFila> comps = new ArrayList<>();
        List<String> fallas = new ArrayList<>();

        for (PuestoCompetencia pc : requeridas) {
            BigDecimal obtenido = c.getPuntajes().get(pc.getCompetencia());
            if (obtenido == null) continue; // competencia descartada al generar este cuestionario

            BigDecimal minimo = BigDecimal.valueOf(pc.getPonderacion());
            boolean cumple = obtenido.compareTo(minimo) >= 0;
            String nombre = pc.getCompetencia().getNombre();
            if (!cumple) fallas.add(nombre);
            comps.add(new CompFila(nombre, num(obtenido), num(minimo), cumple, barra(obtenido, minimo, cumple)));
        }

        return new FilaMerito(
                pos == null ? "-" : String.valueOf(pos),
                pos != null && pos <= 3 ? COLOR_POS[pos - 1] : COLORES[3],
                nombreCompleto(c.getCandidato()),
                documento(c.getCandidato()),
                num(puntaje(c)),
                comps, fallas,
                fecha(c.getFechaInicio()),
                fecha(c.getFechaIntento()),   // no existe fechaFin en tu entidad: se usa el último intento
                String.valueOf(c.getNroAccesos()));
    }

    private FilaSimple filaSimple(Cuestionario c) {
        return new FilaSimple(documento(c.getCandidato()), nombreCompleto(c.getCandidato()),
                fecha(c.getFechaInicio()), fecha(c.getFechaIntento()), String.valueOf(c.getNroAccesos()));
    }

    /**
     * Barra de 0 a 100 armada con celdas de tabla (CSS 2.1 no tiene flex ni posicionamiento fiable).
     * Cumple:    verde hasta el mínimo | marca | verde hasta el puntaje | gris
     * No cumple: rojo hasta el puntaje | gris hasta el mínimo | marca | gris
     */
    private List<Seg> barra(BigDecimal obtenido, BigDecimal minimo, boolean cumple) {
        double v = Math.min(Math.max(obtenido.doubleValue() * 10, 0), 100);
        double m = Math.min(Math.max(minimo.doubleValue() * 10, 0), 100);
        double marca = 0.8, h = marca / 2;

        double[][] partes = cumple
                ? new double[][] { { Math.max(m - h, 0), 0 }, { marca, 1 }, { Math.max(v - m - h, 0), 0 } }
                : new double[][] { { v, 2 }, { Math.max(m - v - h, 0), 3 }, { marca, 1 } };

        List<Seg> segs = new ArrayList<>();
        double usado = 0;
        for (double[] p : partes) {
            if (p[0] > 0.01) {
                segs.add(new Seg(String.format(Locale.ROOT, "%.2f", p[0]), COLORES[(int) p[1]]));
                usado += p[0];
            }
        }
        if (usado < 99.99) {
            segs.add(new Seg(String.format(Locale.ROOT, "%.2f", 100 - usado), COLORES[3]));
        }
        return segs;
    }

    // ---- Helpers --------------------------------------------------------------------------

    /** Mismo cálculo que calcularPuntaje de tu servicio (promedio de competencias). */
    private BigDecimal puntaje(Cuestionario c) {
        var valores = c.getPuntajes().values();
        if (valores.isEmpty()) return BigDecimal.ZERO;
        return valores.stream().reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(valores.size()), 2, RoundingMode.HALF_UP);
    }

    private String num(BigDecimal v) {
        NumberFormat nf = NumberFormat.getNumberInstance(AR); // NumberFormat no es thread-safe: uno por llamada
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(v);
    }

    private String fecha(LocalDateTime f) {
        return f == null ? "-" : FECHA.format(f);
    }

    private String nombreCompleto(Candidato c) {
        return c.getApellido() + ", " + c.getNombre();
    }

    private String documento(Candidato c) {
        return c.getTipo() + " " + c.getDocumento();
    }
}