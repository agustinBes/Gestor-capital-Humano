package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.dto.response.evaluadosDto;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;
import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;
import com.practical.consultora_rrhh.modelo.clases.Puesto;

import java.util.List;

/**
 * Funcionalidad requerida en el CU 25 para generar los cuestionarios mediante
 * el identificador de un puesto y un conjunto de Candidatos
 * @author Besigniano Agustin
 * @since 1.0
 */
public interface IgenerarCuestionario {

    List<evaluadosDto> generarCuestionario(Long idPuesto, List<Long> candidato);
}
