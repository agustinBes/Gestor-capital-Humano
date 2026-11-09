package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.dto.request.opcionRespuestaDtoCreate;
import com.practical.consultora_rrhh.dto.response.opcionRespuestaDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerOpcionRespuesta;
import com.practical.consultora_rrhh.services.ServiceOpcionRespuesta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controlador de opciones de respuesta responde a la creacion,modificacion y eliminacion
 * Tiene funciones de listar todas y filtros de busqueda para obtener por nombre
 * o recuperar una sola en base a su ID
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Controller
@RestController
@RequestMapping("/opciones-de-respuesta")
public class ControllerOpcionRespuesta implements IcontrollerOpcionRespuesta {
    private final ServiceOpcionRespuesta service;

    public ControllerOpcionRespuesta(ServiceOpcionRespuesta service) {
        this.service = service;
    }

    @GetMapping("/")
    public String inicio() {
        return "Servidor de Opciones de Respuesta Consultora RRHH funcionando";
    }

    @Override
    @PostMapping()
    public ResponseEntity<opcionRespuestaDto> guardar(@Valid @RequestBody opcionRespuestaDtoCreate dto) {
        opcionRespuestaDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    @Override
    @PatchMapping("/update/{id}")
    public ResponseEntity<opcionRespuestaDto> actualizar(@PathVariable Long id, @Valid @RequestBody opcionRespuestaDtoCreate dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Opción de respuesta eliminada correctamente.");
    }

    @Override
    @GetMapping("/lista")
    public ResponseEntity<List<opcionRespuestaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<opcionRespuestaDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Override
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<opcionRespuestaDto>> obtenerPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.obtenerPorNombre(nombre));
    }
}
