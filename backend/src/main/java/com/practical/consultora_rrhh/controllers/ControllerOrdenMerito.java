package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.dto.response.reporte.evaluacionesOrdenMeritoDto;
import com.practical.consultora_rrhh.dto.response.reporte.puestoOrdenMeritoDto;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerOrdenMerito;
import com.practical.consultora_rrhh.interfaces.services.IserviceOrdenMerito;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador para emitir una orden de merito siguiendo las peticiones que realiza el consultor
 * a lo largo del CU28
 *
 * @author Besigniano Agustin
 * @since 1.0
 */

@Controller
@RestController
@RequestMapping("/emitir-orden-merito")
public class ControllerOrdenMerito implements IcontrollerOrdenMerito {
    private final IserviceOrdenMerito service;

    public ControllerOrdenMerito(IserviceOrdenMerito s) {
        this.service = s;
    }

    /**
     * Filtro de busqueda de los puesto existentes en el sistema
     * Para saber mas revisar {@link puestoFiltrosDto}
     * Dentro del CU representa el flujo principal en los primeros pasos
     * Devuelve una lista con los puestos que coincidan con los parametros de busqueda
     * Revisar {@link puestoOrdenMeritoDto} para ver la estructura devuelta
     */
    @Override
    @GetMapping("/buscar")
    public ResponseEntity<List<puestoOrdenMeritoDto>> buscar(@ModelAttribute puestoFiltrosDto dto) {
        List<puestoOrdenMeritoDto> resultados = service.buscar(dto);
        return ResponseEntity.accepted().body(resultados);
    }

    /**
     * Representa la accion del usuario de presionar  emitir orden De Merito habiendo seleccionado un puesto.
     * Corresponde al paso 5 del flujo principal
     * Devolvemos una lista de evaluaciones donde vemos la lista de candidatos
     * Con cada estado de su formulario correspondiendo al paso 7 del flujo principal
     */
    @Override
    @GetMapping("/emitir-orden-merito/{id}")
    public ResponseEntity<List<evaluacionesOrdenMeritoDto>> emitirOrdenMerito(@PathVariable String id) {
        Long identificador = TextoUtils.identificador(id);
        List<evaluacionesOrdenMeritoDto> resultados = service.emitirOrdenMerito(identificador);
        return ResponseEntity.accepted().body(resultados);
    }

    /**
     * Generamos el reporte para todos los candidatos seleccionados
     * Recibimos la lista de los seleccionados y generamos un reporte para que el consultor imprima si lo desea
     */
    @Override
    @GetMapping(value = "/generar-reporte/puesto/{id}/export", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarReporte(List<Long> cuestionarios, String id) throws ErrorExportException {
        Long identificador = TextoUtils.identificador(id);
        byte[] pdfBytes = service.exportarchequear(cuestionarios, identificador);

        // Genera un nombre con fecha y hora (ej: orden-merito-20261126_2130.pdf)
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        String nombreArchivo = "orden-merito-" + timestamp + ".pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(nombreArchivo)
                        .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
