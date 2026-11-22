package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
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

    
    @PostMapping("/new")
    public ResponseEntity<competenciaDto> guardar(@Valid @RequestBody competenciaDtoCreate dto) {
        competenciaDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    
    @PatchMapping("/update/{id}")
    public ResponseEntity<competenciaDto> actualizar(@PathVariable String id, @Valid @RequestBody competenciaDtoCreate dto) {
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
    public ResponseEntity<List<competenciaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<competenciaDto> obtenerPorId(@PathVariable String id) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.obtenerPorId(identificador));
    }

@GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<competenciaDto>> buscar(@PathVariable String nombre ) {
        return ResponseEntity.ok(service.buscar(nombre));
    }
}



