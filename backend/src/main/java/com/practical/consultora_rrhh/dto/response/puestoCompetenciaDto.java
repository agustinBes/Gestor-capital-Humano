package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;

public record puestoCompetenciaDto(
        Long idCompetencia,
        short ponderacion
) {
public puestoCompetenciaDto(PuestoCompetencia pc){
   this(
            pc.getCompetencia().getId(),
            pc.getPonderacion()
    );
}


}
