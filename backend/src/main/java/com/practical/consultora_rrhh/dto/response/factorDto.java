package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Factor;

/**
 * Devolvemos un factor con su competencia asociada.
 * No devolvemos la lista de factores que conoce la competencia
 * para evitar la recursion infinita
 * @param competencia formato de competencia en donde no se recuperan los factores asociados
 */
public record factorDto(
        Long id,
        String codigo,
        String nombre,
        String descripcion,
        short orden,
        competenciaDtoSinFactores competencia

) {

     public factorDto(Factor x) {
       this(         x.getId(),
                x.getCodigo(),
                x.getNombre(),
               x.getDescripcion(),
               x.getOrden(),
         new competenciaDtoSinFactores(x.getCompetencia())
        );
    }
}
