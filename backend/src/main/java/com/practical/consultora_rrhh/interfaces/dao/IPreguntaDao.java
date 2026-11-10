package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.modelo.clases.Ponderacion;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;

import java.util.List;
import java.util.Map;

public interface IPreguntaDao extends IDaoBase<Long, Pregunta>  {
    List<Pregunta> buscar(preguntaFiltrosDto filtros);

    List<Pregunta>    obtenerPorFactor(Long id);
    boolean existeNombrePorFactorInsensible(String nombre,Long idFactor,Long competencia);
    boolean existePreguntaPorFactorInsensible(String nombre,Long idFactor,Long competencia);
boolean existeOpcionRespuestaEnuso(Long id);

Pregunta actualizar(Pregunta pregunta, Map<String,String> modificaciones, OpcionRespuesta respuesta,List<Ponderacion> ponderaciones);
}
