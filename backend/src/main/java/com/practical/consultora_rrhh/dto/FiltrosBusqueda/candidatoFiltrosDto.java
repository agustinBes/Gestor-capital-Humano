package com.practical.consultora_rrhh.dto.FiltrosBusqueda;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record candidatoFiltrosDto(
      @Positive(message = "El Nro de candidato  no puede ser negativo")
      @Min(value = 1,message = "El Nro de candidato debe ser mayor que 0")
        Long id,
        String nombre,
        String apellido
) {
}
