package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Bloque;

import java.util.List;

/**
 * Estructura auxiliar para constrolar el estado y actualizacion del cuestionario
 * Separando los items a nivel de bloques
 * @param numero representa el numero de bloque asignado
 * @param completado representa un estadp que infora si todos los items de ese bloque ya fueron respondidos
 * @param items representa los items de cuestionario
 */
public record bloqueDto(
       Short numero,
        boolean completado,
        List<itemCuestionarioDto> items
) {

    public bloqueDto(Bloque b){
        this(
                b.getNumeroBloque(),
                b.isCompletado(),
                b.getItemCuestionario().stream()
                        .map(itemCuestionarioDto::new)
                        .toList()
        );
    }

}
