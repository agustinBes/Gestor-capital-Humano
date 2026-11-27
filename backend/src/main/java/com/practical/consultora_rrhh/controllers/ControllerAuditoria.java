package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.dto.response.auditoriaDto;
import com.practical.consultora_rrhh.services.ServiceAuditoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para ver las auditorias creadas. Escapa al alcance de este trabajo otras funcionalidades con las auditorias
 * @author Besigniano Agustin
 * @since  1.0
 */

@RestController
@RequestMapping("/auditorias")
public class ControllerAuditoria {

    private final ServiceAuditoria service;

    public ControllerAuditoria(ServiceAuditoria service) {
        this.service = service;
    }


    @GetMapping("/lista")
    public ResponseEntity<List<auditoriaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

}