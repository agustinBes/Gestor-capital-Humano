package com.practical.consultora_rrhh.dto.request;

import jakarta.validation.constraints.*;

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
        @NotEmpty(message = "se requiere al menos una competencia")
        @Size(min = 2,message = "se requieren al menos dos competencias para dar de alta un puesto")
        Map<Long, @Min(value = 0,message = "una calificacion no puede ser negativa")
        @Max(value=10,message = "Una calificacion no puede ponderar por encima de 10 puntos")
                Short> caracteristicas
        ) {
}
