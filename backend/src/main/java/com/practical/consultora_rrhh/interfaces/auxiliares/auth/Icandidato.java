package com.practical.consultora_rrhh.interfaces.auxiliares.auth;

import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.modelo.clases.Candidato;

import java.util.List;

public interface Icandidato {

    List<candidatoDto> importarCandidatos(List<candidatoDto> candidato);
    candidatoDto crearCandidato(candidatoDto candidato);
    Candidato obtenerCandidato(Long id);
    Candidato actualizarCandidato(Long candidato,String clave);
void eliminarCandidato(Long id);

}
