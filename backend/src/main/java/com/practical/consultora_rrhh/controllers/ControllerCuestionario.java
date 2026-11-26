package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.cuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;
import com.practical.consultora_rrhh.dto.response.evaluadosDto;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.BloqueCompletadoException;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerCuestionario;
import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.modelo.propiedades.CuestionarioProperties;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
public class ControllerCuestionario implements IcontrollerCuestionario {
    private final IserviceCuestionario service;
    private CuestionarioProperties propiedades;

    public ControllerCuestionario(IserviceCuestionario service, CuestionarioProperties propiedades) {
        this.service = service;
        this.propiedades = propiedades;
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

    @PatchMapping("/cuestionario/{id}/editar")
    @Override
    public ResponseEntity<cuestionarioDto> preguntaContestadas(@Valid cuestionarioDtoCreate cuestionario, @PathVariable String id) {
        Long identificador = TextoUtils.identificador(id);
        if(cuestionario.items().size()!=propiedades.getPreguntasPorBloque()){
            throw new BloqueCompletadoException("Se deben contestar todas las preguntas del bloque", HttpStatus.BAD_REQUEST);
        }
cuestionarioDto actual=service.actualizarCuestionario(cuestionario,identificador);
        return ResponseEntity.ok().body(actual);
    }
    @GetMapping("/cuestionarios/list")
    public  ResponseEntity<List<cuestionarioDto>> obtenerCuestionarios(){
       List<cuestionarioDto> lista= service.obtenerCuestionarios();
    return ResponseEntity.ok().body(lista);
    }

}
