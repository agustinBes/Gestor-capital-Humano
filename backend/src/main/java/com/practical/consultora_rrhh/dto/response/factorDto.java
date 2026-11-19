package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Factor;

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
