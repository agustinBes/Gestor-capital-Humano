package com.practical.consultora_rrhh.dto.response.reporte;

public record candidatoCompletoReporteOrdenMerito(
        candidatoReporteOrdenMerito candidato,
        int puntaje,
        String fechainicio,
        String fechaFin,
        int accesos
) {
}
