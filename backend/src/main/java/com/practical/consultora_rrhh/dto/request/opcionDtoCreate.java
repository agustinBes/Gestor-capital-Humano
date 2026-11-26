package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.modelo.clases.Opcion;
import jakarta.validation.constraints.*;

/**
 * Usado para transferir informacion ademas de la gestion de creacion y modificacion
 *
 * @author  Besigniano Agustin
 * @since 1.0
 *
 *Atributos de validaciones abiertos a modificaciones y revisiones
 *
 */
public record opcionDtoCreate(
        @NotNull(message = "La fila no puede ser null y debe ser de tipoDocumento entero")
        @Min(value = 1, message = "La fila debe ser mayor a cero")
        @Max(value = 32767, message = "El valor máximo es 32767")
        Short fila,

        @NotNull(message = "Se requiere establecer el orden de visualización")
        @Min(value = 1, message = "El orden de visualización debe ser mayor a cero")
        @Max(value = 32767, message = "El valor máximo es 32767")
        Short orden,

            @NotBlank(message = "Se requiere al menos una oración que describa la opción")
        @Size(max=100, message = "Longitud excedida")
        String descripcion

) {

}