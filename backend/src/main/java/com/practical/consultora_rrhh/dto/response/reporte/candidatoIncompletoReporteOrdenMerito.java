package com.practical.consultora_rrhh.dto.response.reporte;

public record candidatoIncompletoReporteOrdenMerito(
        candidatoReporteOrdenMerito candidato,
        String fechaInicio,
        String fechaIntento,
        int accesos

) {
}
