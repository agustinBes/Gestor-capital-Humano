package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.dto.request.opcionRespuestaDtoCreate;
import com.practical.consultora_rrhh.dto.response.opcionRespuestaDto;
import com.practical.consultora_rrhh.interfaces.auxiliares.InternalService;
import com.practical.consultora_rrhh.interfaces.genericos.IserviceCRUD;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;

import java.util.List;

/**
        * Interface que hereda de {@link IserviceCRUD} y {@link InternalService} heredando un conjunto de operaciones basicas
 * que la mayoria de los servicios requieren o exigen para
 * Garantizar la comunicacion entre servicios y con el controlador
 *
 * @author Besigniano Agustin
 *  * @since 1.0
        */

public interface IserviceOpcionRespuesta extends IserviceCRUD<Long, opcionRespuestaDto, opcionRespuestaDtoCreate>, InternalService<OpcionRespuesta,Long> {

    public List<opcionRespuestaDto> obtenerPorNombre(String nombre);

}
