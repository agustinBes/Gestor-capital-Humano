package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.interfaces.auxiliares.IvalidarNombreCodigoDao;
import com.practical.consultora_rrhh.modelo.clases.Competencia;

import java.util.List;

public interface ICompetenciaDao extends IDaoBase<Long, Competencia>, IvalidarNombreCodigoDao {

    List<Competencia> obtenerPorNombreInsensible(String nombre);
}
