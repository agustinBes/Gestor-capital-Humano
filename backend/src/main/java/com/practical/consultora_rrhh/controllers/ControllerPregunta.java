package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.dto.request.preguntaDtoCreate;
import com.practical.consultora_rrhh.dto.response.preguntaDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerPregunta;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/preguntas")
public class ControllerPregunta implements IcontrollerPregunta {

private final IservicePregunta service;

    public ControllerPregunta(IservicePregunta service) {
        this.service = service;
    }

    @Override
    @PostMapping("/new")
    public ResponseEntity<preguntaDto> guardar(@Valid @RequestBody preguntaDtoCreate dto) {
        preguntaDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    @Override
    @PatchMapping("/update/{id}")
    public ResponseEntity<preguntaDto> actualizar(@PathVariable String id, @Valid @RequestBody preguntaDtoCreate dto) {
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

    @Override
    @GetMapping("/lista")
    public ResponseEntity<List<preguntaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<preguntaDto> obtenerPorId(@PathVariable String id) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.obtenerPorId(identificador));
    }

    @Override()
@GetMapping("/buscar/")
    public ResponseEntity<List<preguntaDto>> buscar(@ModelAttribute preguntaFiltrosDto dto) {
        return ResponseEntity.ok(service.buscar(dto));
    }
}



