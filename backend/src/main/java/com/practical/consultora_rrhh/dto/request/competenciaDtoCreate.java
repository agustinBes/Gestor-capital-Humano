package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.modelo.enums.tipoCompetencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record competenciaDtoCreate(
        @NotBlank(message = "se requiere un codigo unico")
        @Size(max = 30,message = "longitud de codigo excedida")
        String codigo,
        @NotBlank(message = "se requiere un nombre para el factor")
        @Size(max = 50,message = "longitud del nombre excedida")
        String nombre,
        @NotBlank(message = "se requiere una descripcion del factor")
        @Size(min=30, max = 300,message = "Se requiere una descripcion de al menos 30 caracteres y no superior a los 300")
        String descripcion,
        @NotNull(message="Se requiere seleccionar al menos una opcion")
        tipoCompetencia tipo

) {
}
