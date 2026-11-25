package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record itemCuestionarioDtoCreate(
        @NotNull(message = "Se requiere un factor")
        @Positive(message = "el id no puede ser negativo")
        @Min(value = 1,message = "el id debe ser mayor que 0")
        Long idpregunta,
        @Positive(message = "el bloque no puede ser negativo")
        @Min(value = 1,message = "el id debe ser mayor que 0")
        Short NroBloque,
        @NotNull(message = "Se debe seleccionar una opcion")
        Short opcion
) {
}
