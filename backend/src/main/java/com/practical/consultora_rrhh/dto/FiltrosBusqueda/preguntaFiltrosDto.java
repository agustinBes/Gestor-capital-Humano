package com.practical.consultora_rrhh.dto.FiltrosBusqueda;

/**
 * Filtros de busqueda para una pregunta
 * @param competencia buscar por codigo o Nombre de competencia
 * @param factor buscar por codigo o Nombre de factor
 * @param pregunta buscar por nombre de pregunta
 * @author Besigniano Agustin
 * @since 1.0
 */

public record preguntaFiltrosDto(
        String competencia,
        String factor,
        String pregunta) {
}
