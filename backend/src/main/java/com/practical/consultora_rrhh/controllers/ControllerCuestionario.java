package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.dto.response.evaluadosDto;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerCuestionario;
import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class ControllerCuestionario implements IcontrollerCuestionario {
    private final IserviceCuestionario service;

    public ControllerCuestionario(IserviceCuestionario service) {
        this.service = service;
    }

    @Override
    @PostMapping("/gestion-cuestionarios/nuevo-cuestionario/{idPuesto}")
    public ResponseEntity<List<evaluadosDto>> evaluarCandidatos( @RequestBody List<Long> candidatos, @PathVariable String idPuesto) {
        Long identificador = TextoUtils.identificador(idPuesto);
        List<evaluadosDto> result = service.generarCuestionario(identificador, candidatos);
        return ResponseEntity.ok().body(result);
    }

    @Override
    @PostMapping("/gestion-cuestionarios/candidatos/export")
    public ResponseEntity<byte[]> exportarCandidatosEvaluados(@RequestBody List<evaluadosDto> datos) throws ErrorExportException {
        byte[] result=service.exportar(datos);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=evaluados.csv")
                .contentType(MediaType.parseMediaType("text/csv ; charset=UTF-8"))
                .body(result);
    }
}
