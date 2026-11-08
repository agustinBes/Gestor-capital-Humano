package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerCompetencia;
import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/competencias")
public class ControllerCompetencia implements IcontrollerCompetencia {

private final IserviceCompetencia service;

    public ControllerCompetencia(IserviceCompetencia service) {
        this.service = service;
    }
    @GetMapping("/")
    public String inicio() {
        return "Servidor de competencias Consultora RRHH funcionando";
    }

    
    @PostMapping()
    public ResponseEntity<competenciaDto> guardar(@Valid @RequestBody competenciaDtoCreate dto) {
        competenciaDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    
    @PatchMapping("/update/{id}")
    public ResponseEntity<competenciaDto> actualizar(@PathVariable Long id, @Valid @RequestBody competenciaDtoCreate dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Opción de respuesta eliminada correctamente.");
    }

    
    @GetMapping("/lista")
    public ResponseEntity<List<competenciaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<competenciaDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

@GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<competenciaDto>> buscar(@PathVariable String nombre ) {
        return ResponseEntity.ok(service.buscar(nombre));
    }
}



