package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.enums.tipoCompetencia;

import java.util.List;

public record competenciaDto(
        Long id,
        String codigo,
        List<factorDto> competencia,
        String nombre,
        String descripcion,
        String tipo
) {
public competenciaDto(Competencia x,boolean factores){
    this(
            x.getId(),
            x.getCodigo(),
          factores?  x.getFactores().stream()
                    .map(o->new factorDto(o,factores))
                    .toList():null,
            x.getNombre(),
            x.getDescripcion(),
            tipoCompetencia.desdeTipo(x.getTipoCompetencia())
    );
}
}
