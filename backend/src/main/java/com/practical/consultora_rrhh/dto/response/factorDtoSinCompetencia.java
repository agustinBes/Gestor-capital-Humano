package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Factor;

public record factorDtoSinCompetencia(Long id,
                                      String codigo,
                                      String nombre,
                                      String descripcion,
                                      short orden,
                                      boolean eliminacion

) {

    public factorDtoSinCompetencia(Factor x) {
        this(         x.getId(),
                x.getCodigo(),
                x.getNombre(),
                x.getDescripcion(),
                x.getOrden(),
                x.isEliminacion());

}
}
