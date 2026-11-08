package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Pregunta;

import java.util.List;

public record preguntaDto(
        Long id,
        factorDto factor,
        String nombre,
        String pregunta,
        String descripcion,
        opcionRespuestaDto respuesta,
        List<ponderacionDto> ponderaciones,
        boolean eliminacion
) {

    public preguntaDto(Pregunta x){

        this(
                x.getId(),
                new factorDto(x.getFactor()),
                x.getNombre(),
                x.getPregunta(),
                x.getDescripcion(),
                new opcionRespuestaDto(x.getOpcionRespuesta()),
                x.getPonderaciones()
                        .stream()
                        .map(ponderacionDto::new)
                        .toList(),
                x.getEliminacion()
                );
    }

}
