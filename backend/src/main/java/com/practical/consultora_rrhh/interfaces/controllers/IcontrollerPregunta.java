package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.dto.request.preguntaDtoCreate;
import com.practical.consultora_rrhh.dto.response.preguntaDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
/**
 * Especializacion de la interfaz generica {@link IControllerCRUD}
 * Definiendo los tipos de datos de los parametros genericos
 *
 * Agregamos cualquier peticion extra que deba garantizar el controlador
 * sean filtros de busqueda, obtener por tal atributo o cualquier operacion relevante
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface IcontrollerPregunta extends IControllerCRUD<preguntaDto, preguntaDtoCreate> {
    /**
     * Permite realizar búsquedas combinadas mediante los siguientes filtros:
     * <ul>
     *     <li>Factor: búsqueda por nombre o código.</li>
     *     <li>Competencia: búsqueda por nombre o código.</li>
     *     <li>Pregunta: búsqueda por su contenido mediante coincidencias parciales.</li>
     * </ul>
     *
     * <p>Los filtros son opcionales y pueden combinarse entre sí.
     *     */
ResponseEntity<List<preguntaDto>> buscar(preguntaFiltrosDto dto);

}
