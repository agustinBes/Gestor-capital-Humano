package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.interfaces.dao.consultas.IConsultaNombreCodigoDao;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.Competencia;

import java.util.List;

/**
 * Interfaz especializa de la interfaz con operaciones genericas IDaoBase {@link IDaoBase}
 * tales como guardar,eliminar,listar, obtenr por cierto criterio de busqueda o campo
 * Agregamos los metodos especificados de la entidad que se requieran
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface ICompetenciaDao extends IDaoBase<Long, Competencia>, IConsultaNombreCodigoDao,IreactivarEntidad<Competencia> {

    List<Competencia> buscar(String nombre);
    Competencia actualizar(Competencia competencia,String nombre,String descripcion);
}
