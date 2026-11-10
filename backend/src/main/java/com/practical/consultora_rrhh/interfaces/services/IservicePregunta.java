package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.dto.request.preguntaDtoCreate;
import com.practical.consultora_rrhh.dto.response.preguntaDto;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;

import java.util.List;

/**
 *
 */
public interface IservicePregunta extends IserviceCRUD<Long,preguntaDto, preguntaDtoCreate>, InternalService<Pregunta,Long> {


    List<preguntaDto> buscar(preguntaFiltrosDto dto);
    void eliminarPorFactor(Long id);

    /**
     * Consulta particular para el servicio de OpcionRespuesta para ver si permite la eliminacion de una Entidad
     */
    boolean existeOpcionRespuestaEnUso(Long id);

}
