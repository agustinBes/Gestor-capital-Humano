package com.practical.consultora_rrhh.dto.response.reporte;

public record evaluacionesOrdenMeritoDto(
        Long idCuestionario,
        Long idCandidato,
        String nombreCandidato,
       //fecha intento
        String fecha,
        String estado
) {
}
