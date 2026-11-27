package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Bloque;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;

import java.util.List;

/**
 * Formato para presentar los datos del cuestionario al candidato usando
 * la estructura auxiliar de bloques {@link bloqueDto}
 * @param idCuestionario
 * @param idCandidato
 * @param idPuesto
 * @param bloques
 */
public record cuestionarioDto(
        Long idCuestionario,
        Long idCandidato,
        Long idPuesto,
        List<bloqueDto> bloques
) {
    public cuestionarioDto(List<Bloque> bloque, Cuestionario actualizado) {
        this(
                actualizado.getId(),
                actualizado.getCandidato().getId(),
                actualizado.getPuesto().getId(),
                bloque.stream()
                        .map(bloqueDto::new)
                        .toList()
        );

    }
}
