package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.dto.response.opcionRespuestaDto;
import com.practical.consultora_rrhh.dto.response.ponderacionDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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

public record factorDtoCreate(
        @NotNull(message = "se requiere asocicar el factor a un competencia")
        Long id_competencia,
        @NotBlank(message = "se requiere un codigo unico")
        @Size(max = 30,message = "longitud de codigo excedida")
        String codigo,
        @NotBlank(message = "se requiere un nombre para el factor")
        @Size(max = 50,message = "longitud del nombre excedida")
        String nombre,
        @NotBlank(message = "se requiere una descripcion del factor")
        @Size(min=30, max = 300,message = "Se requiere una descripcion de al menos 30 caracteres y no superior a los 300")
        String descripcion,
        @NotNull(message = "se requiere establecer un numero de orden")
        @Positive(message = "el numero de ordeno no puede ser 0 o negativo")
        short nroOrden
) {
}
