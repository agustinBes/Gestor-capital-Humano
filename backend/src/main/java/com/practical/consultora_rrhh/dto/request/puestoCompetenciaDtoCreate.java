package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record puestoCompetenciaDtoCreate(
        @NotBlank(message = "Inconsistencia de datos")
        Puesto puesto,
        @NotBlank(message = "Inconsistencia de datos")
Competencia competencia,
        short Ponderacion
) {
}
