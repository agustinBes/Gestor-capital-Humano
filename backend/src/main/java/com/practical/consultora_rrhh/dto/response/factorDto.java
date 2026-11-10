package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Factor;

public record factorDto(
        Long id,
        String codigo,
        competenciaDtoSinFactores competencia,
        String nombre,
        String descripcion,
        short orden,
        boolean eliminacion

) {

     public factorDto(Factor x) {
       this(         x.getId(),
                x.getCodigo(),
                new competenciaDtoSinFactores(x.getCompetencia()),
                x.getNombre(),
                x.getDescripcion(),
                x.getOrden(),
                x.isEliminacion()
        );
    }
}
