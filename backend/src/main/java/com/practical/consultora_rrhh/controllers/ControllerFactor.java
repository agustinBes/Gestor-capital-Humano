package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
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


    @PostMapping("/new")
    public ResponseEntity<factorDto> guardar(@Valid @RequestBody factorDtoCreate dto) {
        factorDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<factorDto> actualizar(@PathVariable String id, @Valid @RequestBody factorDtoCreate dto) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.actualizar(identificador, dto));
    }


    @Override
    @DeleteMapping("/delete/{id}/responsable/{idConsultor}")
    public ResponseEntity<String> eliminar(@PathVariable String id,  @PathVariable  String idConsultor) {
        Long identificador= TextoUtils.identificador(id);
        Long consultor= TextoUtils.identificador(idConsultor);
        String respuesta=  service.eliminar(identificador, consultor);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<factorDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<factorDto> obtenerPorId(@PathVariable String id) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.obtenerPorId(identificador));
    }

    @GetMapping("/buscar")

    public ResponseEntity<List<factorDto>> buscar(@ModelAttribute factorFiltrosDto dto) {
                return ResponseEntity.ok(service.buscar(dto));
    }
}