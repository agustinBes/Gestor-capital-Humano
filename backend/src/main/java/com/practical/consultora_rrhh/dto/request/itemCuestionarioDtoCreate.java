package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;
import com.practical.consultora_rrhh.modelo.clases.Opcion;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Estructua para recibir una pregunta contestada por el usuario y garantizar que sea sobre
 * el cuestionari
 * @param idpregunta identificador de pregunta en la entidad {@link ItemCuestionario}
 * @param NroBloque numero de bloque asociado del item
 * @param opcion opcion elegida en base a la fila {@link Opcion}
 */
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
