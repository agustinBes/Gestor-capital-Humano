package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Pregunta;

import java.util.List;

public record preguntaDto(
        Long id,
        String nombre,
        String pregunta,
        String descripcion,
        opcionRespuestaDto respuesta,
        factorDto factor,
        List<ponderacionDto> ponderaciones
) {

    public preguntaDto(Pregunta x){

        this(
                x.getId(),
                x.getNombre(),
                x.getPregunta(),
                x.getDescripcion(),
                new opcionRespuestaDto(x.getOpcionRespuesta()),
                new factorDto(x.getFactor()),
                x.getPonderaciones()
                        .stream()
                        .map(ponderacionDto::new)
                        .toList()
                );
    }

}
