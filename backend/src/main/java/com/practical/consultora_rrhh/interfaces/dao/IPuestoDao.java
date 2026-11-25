package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.interfaces.dao.consultas.IConsultaNombreCodigoDao;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.Puesto;

import java.util.List;
import java.util.Map;

public interface IPuestoDao extends IDaoBase<Long, Puesto>, IConsultaNombreCodigoDao,IreactivarEntidad<Puesto>{
    Puesto actualizar(Puesto actual, Map<String, String> modificacion);

    List<Puesto> buscar(puestoFiltrosDto dto);
}
