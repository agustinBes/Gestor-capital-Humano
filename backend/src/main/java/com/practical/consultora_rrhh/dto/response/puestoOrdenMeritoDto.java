package com.practical.consultora_rrhh.dto.response;

public record puestoOrdenMeritoDto(
        String codigo,
        String nombre,
        String empresa,
        int candidatos,
        int evaluacionesCompletadas
) {
}
