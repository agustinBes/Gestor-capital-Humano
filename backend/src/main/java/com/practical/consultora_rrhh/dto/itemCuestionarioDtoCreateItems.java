package com.practical.consultora_rrhh.dto;

import com.practical.consultora_rrhh.modelo.clases.Opcion;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;

/**
 * Estructura auxiliar para generar los items antes de ser persistidos
 * @param pregunta  Pregunta asociada a ese item
 * @param NroBloque Nro de bloque al que pertenece
 * @param Puntaje Ponderacion obtenida inicialmente en 0
 * @param opcion Fila selecciona de {@link Opcion}
 */
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
