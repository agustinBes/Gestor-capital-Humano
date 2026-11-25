package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.modelo.clases.Pregunta;

public record itemCuestionarioDtoCreate(
        Pregunta pregunta,
        Short NroBloque,
        Short Puntaje,
        Short opcion)

{


    public itemCuestionarioDtoCreate(Pregunta p, Short bloque) {
       this(
                p,
                bloque,
               (short) 0 ,
               null);

    }

}
