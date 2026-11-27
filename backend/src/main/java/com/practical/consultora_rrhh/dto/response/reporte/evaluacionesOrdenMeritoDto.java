package com.practical.consultora_rrhh.dto.response.reporte;
/**
 * Formato de datos que recibe el usuario durante el flujo del CU de emitir orden de merito
 */
public record evaluacionesOrdenMeritoDto(
        Long idCuestionario,
        Long idCandidato,
        String nombreCandidato,
       //fecha intento
        String fecha,
        String estado
) {
}
