package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.response.factorDto;
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

public interface IcontrollerFactor extends IControllerCRUD<factorDto,factorDtoCreate> {

   /**
     * Permite realizar búsquedas combinadas mediante los siguientes filtros:
     * <ul>
     *     <li>Factor: búsqueda por nombre.</li>
     *     <li>Competencia: búsqueda por nombre.</li>
     * </ul>
     *
     * <p>Los filtros son opcionales y pueden combinarse entre sí.
     *     */



    ResponseEntity<List<factorDto>> buscar(factorFiltrosDto dto);
}
