package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Ponderacion;

/**
 * Puntaje asignado por una pregunta a una opcion de la opcion de respuesta
 * que tiene asociada la pregunta
 * @param idOpcionRespuesta
 * @param opcion representa la fila de la opcion que es inalterable una vez creada
 * @param puntaje
 */
public record ponderacionDto(
Long idOpcionRespuesta,
        short opcion,
        short puntaje
) {
public ponderacionDto(Ponderacion x){
    this(
            x.getOpcionRespuesta(),
            x.getFila(),
            x.getPuntaje()
    );
}

}
