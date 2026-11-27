package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.dto.response.reporte.evaluacionesOrdenMeritoDto;
import com.practical.consultora_rrhh.dto.response.puestoOrdenMeritoDto;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import com.practical.consultora_rrhh.interfaces.services.IserviceOrdenMerito;
import com.practical.consultora_rrhh.modelo.clases.*;
import com.practical.consultora_rrhh.modelo.enums.tipoEstado;
import com.practical.consultora_rrhh.services.mediator.OrdenMeritoCuestionarioMediator;
import com.practical.consultora_rrhh.services.mediator.OrdenMeritoPuestoMediator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
@Service
public class ServiceOrdenMerito implements IserviceOrdenMerito {
private final OrdenMeritoPuestoMediator puestoMediator;
private final OrdenMeritoCuestionarioMediator cuestionarioMediator;
private final OrdenMeritoPdfRenderer pdfRenderer;

    public ServiceOrdenMerito(OrdenMeritoPuestoMediator puestoMediator, OrdenMeritoCuestionarioMediator cuestionarioMediator, OrdenMeritoPdfRenderer pdfRenderer) {
        this.puestoMediator = puestoMediator;
        this.cuestionarioMediator = cuestionarioMediator;
        this.pdfRenderer = pdfRenderer;
    }

    @PostConstruct
    public void init(){
        puestoMediator.registrarServicioA(this);
        cuestionarioMediator.registrarServicioA(this);
    }

    @Override
    public List<puestoOrdenMeritoDto> buscar(puestoFiltrosDto dto) {
     Map<Puesto,List<PuestoCompetencia>> puestos=puestoMediator.obtenerPuestos(dto);
        List<Long> listPuestos=puestos.keySet().stream().map(Puesto::getId).toList();

        List<Cuestionario> cuestionarios=cuestionarioMediator.obtenerPorPuestos(listPuestos);
List<puestoOrdenMeritoDto> resultados=new ArrayList<>();

        for(Puesto p:puestos.keySet()) {
            long candidatos = cuestionarios.stream()
                    .filter(c -> c.getPuesto().equals(p))
                    .count();

            long completado = cuestionarios.stream()
                    .filter(c -> c.getPuesto().equals(p))
                    .filter(c -> c.getEstado().equals(tipoEstado.COMPLETADO))
                    .count();

            resultados.add(new puestoOrdenMeritoDto(p.getCodigo(), p.getNombre(), p.getEmpresa(), (int)(candidatos),(int)(completado)));

        }
        return resultados;}

    @Override
    public List<evaluacionesOrdenMeritoDto> emitirOrdenMerito(Long id) {

        List<Cuestionario> cuestionarios = cuestionarioMediator.obtenerPorPuesto(id);
        List<evaluacionesOrdenMeritoDto> resultados = new ArrayList<>();

        for (Cuestionario c : cuestionarios) {
            Long idC = c.getId();
            Long idCandidato = c.getCandidato().getId();
            String nombre = c.getCandidato().getNombre();
            String estado = tipoEstado.desdeTipo(c.getEstado());
            LocalDateTime fecha = c.getFechaIntento();
            String f = fecha.toString();
            evaluacionesOrdenMeritoDto nuevo = new evaluacionesOrdenMeritoDto(idC, idCandidato, nombre, estado, f);
            resultados.add(nuevo);}
        return resultados;
    }

    @Override
    public byte[] exportar(List<Long> cuestionarios) {
        List<Cuestionario> cuestionario=cuestionarioMediator.obtenerCuestionarios(cuestionarios);
    List<Cuestionario> completados=cuestionario.stream()
            .filter(o->o.getEstado().equals(tipoEstado.COMPLETADO))
            .toList();
    List<Cuestionario> incompletos=cuestionario.stream()
            .filter(o->o.getEstado().equals(tipoEstado.INCOMPLETO))
            .toList();
    List<Cuestionario> sinContestar=cuestionario.stream()
            .filter(o->o.getEstado().equals(tipoEstado.SIN_CONTESTAR))
            .toList();
List<Candidato>  candidatos=cuestionario
        .stream()
        .map(Cuestionario::getCandidato)
        .distinct()
        .toList();
byte[] nuevo=generarReporte(completados,incompletos,sinContestar);
return nuevo;
   }
public
    @Override
     byte[] generarReporte(List<Cuestionario> completo, List<Cuestionario> incompleto, List<Cuestionario> sinContestar) {

    Cuestionario cualquiera = Stream.of(completo, incompleto, sinContestar)
            .flatMap(List::stream)
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("El puesto no posee evaluaciones")); // alt. 6.A
    Puesto puesto = cualquiera.getPuesto();

  //Obtener las competencias asociadas al puesto
        List<PuestoCompetencia> competencias=puestoMediator.obtenerCompetencias(puesto.getId());
//obtener las competencias evaluadas en el cuestionario
        List<Competencia>   competenciasEvaluadas=completo.getFirst().getPuntajes().keySet().stream().toList();
//obtener ponderaciones de competencias evaluadas en el cuestionario descartando las no evaluadas
List<PuestoCompetencia> evaluadas=new ArrayList<>();
for(Competencia c:competenciasEvaluadas) {
    PuestoCompetencia pc = competencias.stream()
            .filter(o -> o.getCompetencia().equals(c))
            .findFirst().orElse(null);
    if (pc != null) {        evaluadas.add(pc);}
}
     List<Cuestionario> aprobados=completo.stream()
                .filter(o-> aprobados(o,evaluadas) )
             .toList();
        List<Cuestionario> noAprobados=completo.stream()
                .filter(o->! aprobados(o,evaluadas) )
                .toList();


return  pdfRenderer.renderizar(puesto, evaluadas, aprobados, noAprobados, incompleto, sinContestar);
    }

    private boolean aprobados(Cuestionario c,List<PuestoCompetencia> p) {
        for(PuestoCompetencia pc:p) {
            Competencia competencia=pc.getCompetencia();
            short requerido=pc.getPonderacion();
            BigDecimal puntos=c.getPuntajes().get(competencia);
            if(puntos!=null){

                if(puntos.doubleValue()<requerido){
return false;
                }
            }
        }

return true;
    }
private BigDecimal calcularPuntaje(Cuestionario c){
    BigDecimal aux= c.getPuntajes().values().stream()
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(2, RoundingMode.HALF_UP);
int cantidad=c.getPuntajes().size();
    if (cantidad > 0) {
        aux = aux.divide(BigDecimal.valueOf(cantidad), 2, RoundingMode.HALF_UP);
    } else {
        aux = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }
return aux;}
}
