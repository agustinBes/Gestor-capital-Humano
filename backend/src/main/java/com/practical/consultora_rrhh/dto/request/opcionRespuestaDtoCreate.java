package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

/**
 * Usado para transferir informacion ademas de la gestion de creacion y modificacion
 *
 * @author  Besigniano Agustin
 * @since 1.0
 *
 *Atributos de validaciones abiertos a modificaciones y revisiones
 *
 */
public record opcionRespuestaDtoCreate(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max=50, message = "Longitud excedida")
        String nombre,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max=300, message = "Longitud excedida")
        String descripcion,

        @NotEmpty(message = "Se requieren opciones de respueta")
        @Size(min = 2, message = "Se requieren al menos dos opciones")
        List<@Valid opcionDtoCreate> opciones

) {


}
