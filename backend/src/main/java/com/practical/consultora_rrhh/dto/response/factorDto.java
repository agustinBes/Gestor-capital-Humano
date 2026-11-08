package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Factor;

public record factorDto(
        Long id,
        String codigo,
        competenciaDto competencia,
        String nombre,
        String descripcion,
        short orden,
        boolean eliminacion

) {

    public factorDto(Factor x){
        this(
                x.getId(),
                x.getCodigo(),
             new competenciaDto(x.getCompetencia(),false),
                x.getNombre(),
                x.getDescripcion(),
                x.getOrden(),
                x.isEliminacion()
        );
    }
}
