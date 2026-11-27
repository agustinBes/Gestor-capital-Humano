package com.practical.consultora_rrhh.dto.response.reporte;

/**
 * Formato de datos que recibe el usuario luego de buscar ocon los filtros en
 * el CU 28
 */
public record puestoOrdenMeritoDto(
       Long id,
        String codigo,
        String nombre,
        String empresa,
        int candidatos,
        int evaluacionesCompletadas
) {
}
