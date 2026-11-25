package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.dto.response.evaluadosDto;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IcontrollerCuestionario  {

    
ResponseEntity<List<evaluadosDto>>  evaluarCandidatos(@Valid List<Long> candidatos, String idPuesto);
ResponseEntity<byte[]>  exportarCandidatosEvaluados(List<evaluadosDto> datos) throws ErrorExportException;

}
