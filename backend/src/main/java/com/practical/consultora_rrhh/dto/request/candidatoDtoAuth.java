package com.practical.consultora_rrhh.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record candidatoDtoAuth(
        @NotBlank(message = "El tipoDocumento de documento es obligatorio")
        @Pattern(regexp = "^(DNI|LE|LC|PP)$", message = "El tipoDocumento de documento debe ser DNI, LE, LC o PP")
       String tipoDocumento,

        @NotBlank(message = "El número de documento es obligatorio")
        @Pattern(regexp = "^[0-9A-Za-z]{6,15}$", message = "El documento debe tener entre 6 y 15 caracteres alfanuméricos sin puntos ni guiones")
        String documento,
        @NotBlank(message = "la clave no puede estar en blanco")
        @Size(min=8,max=8,message = "revisa el tamaño de la clave de ingreso")
        String clave
) {
}
