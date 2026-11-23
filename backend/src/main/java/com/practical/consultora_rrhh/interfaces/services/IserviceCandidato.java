package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.excepciones.genericas.CorruptFileException;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;

import java.util.List;

public interface IserviceCandidato {

  String importarCandidatos(Resource recurso) throws CorruptFileException;

  List<candidatoDto> obtenerCandidatos();

  Candidato obtenerCandidato(Long id);


    List<candidatoDto> buscarCandidatos(candidatoFiltrosDto filtro);
}
