package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.enums.tipoCompetencia;

/**
 * Evitar la recursion infinita que habia en el constructor dado que factor y competencia se conocen mutuamente
 * @author Besigniano Agustin
 * @since 1.0
 */

public record competenciaDtoSinFactores(
        Long id,
        String codigo,
        String nombre,
        String descripcion,
        String tipo
) {
public competenciaDtoSinFactores(Competencia x){
    this(
            x.getId(),
            x.getCodigo(),
            x.getNombre(),
            x.getDescripcion(),
            tipoCompetencia.desdeTipo(x.getTipoCompetencia())
    );
}
}
