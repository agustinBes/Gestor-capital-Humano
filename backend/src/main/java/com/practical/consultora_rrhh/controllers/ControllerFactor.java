package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerFactor;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/** Controlador de Factores responde a la creacion,modificacion y eliminacion
 * Tiene funciones de listar todas y filtros de busqueda para obtener por nombre
 * o recuperar una sola en base a su ID
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Controller
@RestController
@RequestMapping("/factores")

public class ControllerFactor implements IcontrollerFactor {
    private final IserviceFactor service;
    public ControllerFactor(IserviceFactor serviceFactor) {
        this.service = serviceFactor;
    }
    @GetMapping("/")
    public String inicio() {
        return "Servidor de Factores de Consultora RRHH funcionando";
    }

    @PostMapping()
    public ResponseEntity<factorDto> guardar(@Valid @RequestBody factorDtoCreate dto) {
        factorDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<factorDto> actualizar(@PathVariable Long id, @Valid @RequestBody factorDtoCreate dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Factor eliminado correctamente.");
    }

    @GetMapping("/lista")
    public ResponseEntity<List<factorDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<factorDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/buscar")

    public ResponseEntity<List<factorDto>> buscar(@ModelAttribute factorFiltrosDto dto) {
        return ResponseEntity.ok(service.buscar(dto));
    }
}