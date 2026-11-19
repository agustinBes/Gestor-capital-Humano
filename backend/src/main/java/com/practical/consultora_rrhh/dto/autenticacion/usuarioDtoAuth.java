package com.practical.consultora_rrhh.dto.autenticacion;

public record usuarioDtoAuth(
        String token,
        String nombre,
        Long id
) {
}
