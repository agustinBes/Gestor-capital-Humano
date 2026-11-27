package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Factor;

/**
 * Devolvemos un factor pero sin su competencia asociada
 */
public record factorDtoSinCompetencia(Long id,
                                      String codigo,
                                      String nombre,
                                      String descripcion,
                                      short orden

) {

    public factorDtoSinCompetencia(Factor x) {
        this(         x.getId(),
                x.getCodigo(),
                x.getNombre(),
                x.getDescripcion(),
                x.getOrden());

}
}
