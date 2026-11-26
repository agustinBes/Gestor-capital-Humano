package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;

import java.util.List;

public record itemCuestionarioDto(
            Long idPregunta,
            String titulo,
            String pregunta,
            String descripcion,
            Short opcionSeleccionada,
            List<opcionDto> opciones
    ) {
    public itemCuestionarioDto(ItemCuestionario i){
        this(
                i.getPregunta().getId(),
                i.getPregunta().getNombre(),
                i.getPregunta().getPregunta(),
                i.getPregunta().getDescripcion(),
                i.getOpcion(),
                i.getPregunta().getOpcionRespuesta()
                        .getOpciones()
                        .stream()
                        .map(opcionDto::new)
                        .toList()
        );
    }


}


