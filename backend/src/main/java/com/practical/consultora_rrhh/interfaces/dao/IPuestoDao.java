package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.interfaces.dao.consultas.IConsultaNombreCodigoDao;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.Puesto;

import java.util.List;
import java.util.Map;
/**
 * Interfaz especializa de la interfaz con operaciones genericas IDaoBase {@link IDaoBase}
 * tales como guardar,eliminar,listar, obtenr por cierto criterio de busqueda o campo
 * Agregamos los metodos especificados de la entidad que se requieran
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface IPuestoDao extends IDaoBase<Long, Puesto>, IConsultaNombreCodigoDao,IreactivarEntidad<Puesto>{
    Puesto actualizar(Puesto actual, Map<String, String> modificacion);

    List<Puesto> buscar(puestoFiltrosDto dto);
}
