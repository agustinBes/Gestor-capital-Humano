package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.PreguntaFiltrosDto;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface IPreguntaDao extends IDaoBase<Long, Pregunta>  {
    List<Pregunta> buscar(PreguntaFiltrosDto filtros);

    boolean existeNombrePorFactorInsensible(String nombre,Long idFactor,Long competencia);
    boolean existePreguntaPorFactorInsensible(String nombre,Long idFactor,Long competencia);
boolean existeOpcionRespuestaEnuso(Long id);
}
