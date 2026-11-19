package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.opcionRespuestaDtoCreate;
import com.practical.consultora_rrhh.dto.response.opcionRespuestaDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerOpcionRespuesta;
import com.practical.consultora_rrhh.interfaces.services.IserviceOpcionRespuesta;
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
    private final IserviceOpcionRespuesta service;

    public ControllerOpcionRespuesta(IserviceOpcionRespuesta service) {
        this.service = service;
    }

      @Override
      @PostMapping("/new")
    public ResponseEntity<opcionRespuestaDto> guardar(@Valid @RequestBody opcionRespuestaDtoCreate dto) {
        opcionRespuestaDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    @Override
    @PatchMapping("/update/{id}")
    public ResponseEntity<opcionRespuestaDto> actualizar(@PathVariable String id, @Valid @RequestBody opcionRespuestaDtoCreate dto) {
       Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.actualizar(identificador, dto));
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        Long identificador= TextoUtils.identificador(id);

        String respuesta=  service.eliminar(identificador);
        return ResponseEntity.ok(respuesta);
    }

    @Override
    @GetMapping("/lista")
    public ResponseEntity<List<opcionRespuestaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<opcionRespuestaDto> obtenerPorId(@PathVariable String id) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.obtenerPorId(identificador));
    }

    @Override
    @GetMapping({"/nombre/{nombre}", "/nombre/"})

    public ResponseEntity<List<opcionRespuestaDto>> obtenerPorNombre(@PathVariable(required = false)
                                                                         String nombre) {
        return ResponseEntity.ok(service.obtenerPorNombre(nombre));
    }
}
