package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.interfaces.dao.consultas.IConsultaOpcionRespuestaEnUsoDao;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.modelo.clases.Ponderacion;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;

import java.util.List;
import java.util.Map;
/**
 * Interfaz especializa de la interfaz con operaciones genericas IDaoBase {@link IDaoBase}
 * tales como guardar,eliminar,listar, obtenr por cierto criterio de busqueda o campo
 * Agregamos los metodos especificados de la entidad que se requieran
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface IPreguntaDao extends IDaoBase<Long, Pregunta>, IConsultaOpcionRespuestaEnUsoDao,IreactivarEntidad<Pregunta> {
    List<Pregunta> buscar(preguntaFiltrosDto filtros);

    List<Pregunta>    obtenerPorFactor(Long id);
    boolean existeNombrePorFactorInsensible(String nombre,Long idFactor,Long competencia);
    boolean existePreguntaPorFactorInsensible(String nombre,Long idFactor,Long competencia);


Pregunta actualizar(Pregunta pregunta, Map<String,String> modificaciones, OpcionRespuesta respuesta,List<Ponderacion> ponderaciones);

    List<Pregunta> obtenerPorCompetencia(List<Long> competencias);
}
