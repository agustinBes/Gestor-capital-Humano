package com.practical.consultora_rrhh.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record puestoCompetenciaDtoCreate(
        @NotBlank(message = "Inconsistencia de datos")
        Long idPuesto,
        @NotBlank(message = "Inconsistencia de datos")
        Long idCompetencia,
@NotBlank(message = "Se requiere establecer una ponderaicon valida")
@Min(value = 0,message = "la ponderacion no puede ser negativa")
@Max(value = 10,message = "La ponderacion no puede ser mayor de 10")
        short Ponderacion
) {
}
