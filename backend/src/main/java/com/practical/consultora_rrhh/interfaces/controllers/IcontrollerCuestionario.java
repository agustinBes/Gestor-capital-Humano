package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.request.cuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;
import com.practical.consultora_rrhh.dto.response.evaluadosDto;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Interfaz que contiene los metodos requeridos para los cuestionarios
 * del caso de USO 25 y 26
 * creacion de cuestionarios y actualizacion
 * metodo de exportacion
 */
public interface IcontrollerCuestionario  {

    
ResponseEntity<List<evaluadosDto>>  evaluarCandidatos(@Valid List<Long> candidatos, String idPuesto);
ResponseEntity<byte[]>  exportarCandidatosEvaluados(List<evaluadosDto> datos) throws ErrorExportException;

    ResponseEntity<cuestionarioDto> preguntaContestadas(@RequestBody @Valid cuestionarioDtoCreate cuestionario, @PathVariable String id);
}
