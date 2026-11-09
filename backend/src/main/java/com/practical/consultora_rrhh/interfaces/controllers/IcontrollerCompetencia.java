package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
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
public interface IcontrollerCompetencia extends  IControllerCRUD<competenciaDto, competenciaDtoCreate>{
    /**
     * Permite realizar búsquedas por coincidencias parciales
     */
    ResponseEntity<List<competenciaDto>> buscar(String nombre);

}
