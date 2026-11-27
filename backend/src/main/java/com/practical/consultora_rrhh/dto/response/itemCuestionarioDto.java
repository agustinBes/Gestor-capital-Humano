package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;

import java.util.List;

/**
 * Formato para devolver un item de cuestionario
 * @param idPregunta id_pregunta asociada
 * @param titulo nombre o titulo de la pregunta
 * @param pregunta pregunta realizada
 * @param descripcion descripcion de la misma si la hubiera
 * @param opcionSeleccionada opcion elegida de la lista de opciones
 * @param opciones lista de opciones asociadas a la opcion de respuesta
 */
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


