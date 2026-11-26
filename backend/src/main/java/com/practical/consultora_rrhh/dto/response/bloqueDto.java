package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Bloque;

import java.util.List;

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
