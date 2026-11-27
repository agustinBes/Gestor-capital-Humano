package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.dto.response.reporte.evaluacionesOrdenMeritoDto;
import com.practical.consultora_rrhh.dto.response.puestoOrdenMeritoDto;
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
@Controller
@RestController
@RequestMapping("/emitir-orden-merito")
public class ControllerOrdenMerito implements IcontrollerOrdenMerito {
    private final IserviceOrdenMerito service;

    public ControllerOrdenMerito(IserviceOrdenMerito s) {
        this.service = s;
    }

    @Override
    @GetMapping("/buscar")
    public ResponseEntity<List<puestoOrdenMeritoDto>> buscar(@ModelAttribute puestoFiltrosDto dto) {
        List<puestoOrdenMeritoDto> resultados = service.buscar(dto);
        return ResponseEntity.accepted().body(resultados);
    }

    @Override
    @GetMapping("/emitir-orden-merito/{id}")
    public ResponseEntity<List<evaluacionesOrdenMeritoDto>> emitirOrdenMerito(@PathVariable String id) {
        Long identificador = TextoUtils.identificador(id);
        List<evaluacionesOrdenMeritoDto> resultados = service.emitirOrdenMerito(identificador);
        return ResponseEntity.accepted().body(resultados);
    }

    @Override
    @GetMapping(value = "/generar-reporte/export", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generarReporte(List<Long> cuestionarios) throws ErrorExportException {
        byte[] pdfBytes = service.exportar(cuestionarios);

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
