package com.practical.consultora_rrhh.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public record cuestionarioDtoCreate(
    @NotNull(message = "se requiere un id del candidato")
    @Positive(message = "El id no puede ser negativo")
    @Min(value=1,message="No puede ser 0")
     Long id_candidato,
        @NotNull(message = "se requiere un id del puesto")
        @Positive(message = "El id no puede ser negativo")
        @Min(value=1,message="No puede ser 0")
        Long id_puesto,
        @NotEmpty(message = "Se requiere un conjunto de respuestas")
                    List<@Valid itemCuestionarioDtoCreate> items
) {


}
