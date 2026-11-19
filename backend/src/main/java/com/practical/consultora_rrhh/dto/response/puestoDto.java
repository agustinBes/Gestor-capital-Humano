package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Puesto;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Map;

public record puestoDto(
        Long  idPuesto,
        String codigo,
        String nombre,
        String descripcion,
        String empresa,
        List<puestoCompetenciaDto> caracteristicas
){

public puestoDto(Puesto p, List<PuestoCompetencia> pc)    {
  this(
            p.getId(),
            p.getCodigo(),
            p.getNombre(),
            p.getDescripcion(),
            p.getEmpresa(),
            pc.stream()
              .map(puestoCompetenciaDto::new)
              .toList()
    );
}
  public puestoDto(Puesto p)    {
    this(
            p.getId(),
            p.getCodigo(),
            p.getNombre(),
            p.getDescripcion(),
            p.getEmpresa(),
            null
    );
  }

}
