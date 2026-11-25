package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.modelo.clases.Pregunta;

public record itemCuestionarioDtoCreateItems(
        Pregunta pregunta,
        Short NroBloque,
        Short Puntaje,
        Short opcion)

{


    public itemCuestionarioDtoCreateItems(Pregunta p, Short bloque) {
       this(
                p,
                bloque,
               (short) 0 ,
               null);

    }

}
