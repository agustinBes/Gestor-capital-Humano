package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.request.opcionRespuestaDtoCreate;
import com.practical.consultora_rrhh.dto.response.opcionRespuestaDto;
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
public interface IcontrollerOpcionRespuesta extends IControllerCRUD<opcionRespuestaDto, opcionRespuestaDtoCreate> {
    /**
     * Permite realizar búsquedas por coincidencias parciales
     */
    public ResponseEntity<List<opcionRespuestaDto>> obtenerPorNombre(String nombre);
}
