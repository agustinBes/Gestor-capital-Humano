package com.practical.consultora_rrhh.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record puestoDtoCreate(
        @NotBlank(message = "se requiere un codigo unico")
        @Size(max = 30,message = "longitud de codigo excedida")
        String codigo,
        @NotBlank(message = "se requiere un nombre para el factor")
        @Size(max = 50,message = "longitud del nombre excedida")
        String nombre,
        @NotBlank(message = "se requiere una descripcion del factor")
        @Size(min=30, max = 300,message = "Se requiere una descripcion de al menos 30 caracteres y no superior a los 300")
        String descripcion,
        @NotBlank
        @Size(max = 50, message = "Longitud excedida")
String empresa,

        Map<Long,Short> caracteristicas
        ) {
}
