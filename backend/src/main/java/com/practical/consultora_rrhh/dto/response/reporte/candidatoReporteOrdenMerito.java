package com.practical.consultora_rrhh.dto.response.reporte;

/**
 * Formato de datos que recibe el usuario durante el flujo del CU de emitir orden de merito
 */
public record candidatoReporteOrdenMerito(
        String apellido,
        String nombre,
        String tipoDocumento,
        String documento
) {
}
