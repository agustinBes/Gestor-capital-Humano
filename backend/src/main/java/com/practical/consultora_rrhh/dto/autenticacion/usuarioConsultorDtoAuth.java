package com.practical.consultora_rrhh.dto.autenticacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record usuarioConsultorDtoAuth(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(max = 15,message = "Longitud excedida")
        String nombreUsuario,
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(max = 20,message = "Longitud Excedida")
        String contraseña
) {
}
