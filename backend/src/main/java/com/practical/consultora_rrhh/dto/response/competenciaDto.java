package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.enums.tipoCompetencia;

import java.util.List;

public record competenciaDto(
        Long id,
        String codigo,
        List<factorDtoSinCompetencia> factores,
        String nombre,
        String descripcion,
        String tipo
) {
public competenciaDto(Competencia x){
    this(
            x.getId(),
            x.getCodigo(),
  x.getFactores().stream()
                    .map(factorDtoSinCompetencia::new)
                    .toList(),
            x.getNombre(),
            x.getDescripcion(),
            tipoCompetencia.desdeTipo(x.getTipoCompetencia())
    );
}
}
