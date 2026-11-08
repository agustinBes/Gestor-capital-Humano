package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.response.factorDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

/**
 * Usado para transferir informacion ademas de la gestion de creacion y modificacion
 *
 * @author  Besigniano Agustin
 * @since 1.0
 *
 * dto abierto a modificaciones futuras de ser requerido o rediseñado con mas de 1 dto para cada entidad persistida
 *
 */
public record preguntaDtoCreate(

        @NotNull(message = "Se requiere un factor")
        factorDto factor,

        @NotBlank(message = "Se requiere un título para guardar la pregunta")
        @Size(max = 80, message = "Longitud del título excedida")
         String titulo,

        @NotBlank(message = "Se requiere una pregunta")
        @Size(max = 150, message = "Longitud de la pregunta excedida")
        String pregunta,

        @Size(max=300, message = "La longitud de la descripcion no puede exceder los 300 caracteres")
        String descripcion,

        @NotNull(message = "Se requiere una configuración de opciones")
        Long id_opcionRespuesta,

        @NotEmpty(message = "Se requiere al menos una ponderación")
        List<@Positive(message = "Las ponderaciones deben ser mayores a cero") Short> ponderaciones
) {



}
