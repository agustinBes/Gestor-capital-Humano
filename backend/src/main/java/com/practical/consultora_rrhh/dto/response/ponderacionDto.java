package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Ponderacion;

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
