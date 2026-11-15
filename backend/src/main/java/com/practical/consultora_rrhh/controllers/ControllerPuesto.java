package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.dto.request.puestoDtoCreate;
import com.practical.consultora_rrhh.dto.response.puestoDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerPuesto;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controlador de puestos responde a la creacion,modificacion y eliminacion
 * Tiene funciones de listar todas y filtros de busqueda para obtener por nombre
 * o recuperar una sola en base a su ID
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Controller
@RestController
@RequestMapping("/puestos")

public class ControllerPuesto implements IcontrollerPuesto {
    private final IservicePuesto service;
    public ControllerPuesto(IservicePuesto servicepuesto) {
        this.service = servicepuesto;
    }
    @GetMapping("/")
    public String inicio() {
        return "Servidor de puestoes de Consultora RRHH funcionando";
    }

    @PostMapping()
    public ResponseEntity<puestoDto> guardar(@Valid @RequestBody puestoDtoCreate dto) {
        puestoDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<puestoDto> actualizar(@PathVariable Long id, @Valid @RequestBody puestoDtoCreate dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        String respuesta=  service.eliminar(id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<puestoDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<puestoDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }


}