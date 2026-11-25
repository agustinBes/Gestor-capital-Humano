package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;

public record puestoCompetenciaDto(
        String nombreCompetencia,
        Long idCompetencia,
        short ponderacion
) {
public puestoCompetenciaDto(PuestoCompetencia pc,String nombreCompetencia) {
   this(
           nombreCompetencia,
            pc.getCompetencia().getId(),
            pc.getPonderacion()
    );
}


}
