package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.dto.request.preguntaDtoCreate;
import com.practical.consultora_rrhh.dto.response.preguntaDto;
import com.practical.consultora_rrhh.interfaces.auxiliares.InternalService;
import com.practical.consultora_rrhh.interfaces.genericos.IserviceCRUD;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;

import java.util.List;

/**
 * Interface que hereda de {@link IserviceCRUD} y {@link InternalService} heredando un conjunto de operaciones basicas
 * que la mayoria de los servicios requieren o exigen para
 * Garantizar la comunicacion entre servicios y con el controlador
 *
 * @author Besigniano Agustin
 *  * @since 1.0
 */
public interface IservicePregunta extends IserviceCRUD<Long,preguntaDto, preguntaDtoCreate>, InternalService<Pregunta,Long> {


    List<preguntaDto> buscar(preguntaFiltrosDto dto);
    List<String> eliminarPorFactor(Long id,Long consultor);

    /**
     * Consulta particular para el servicio de OpcionRespuesta para ver si permite la eliminacion de una Entidad
     */
    boolean existeOpcionRespuestaEnUso(Long id);

    List<Pregunta> obtenerPorCompetencia(List<Competencia> competencias);
}
