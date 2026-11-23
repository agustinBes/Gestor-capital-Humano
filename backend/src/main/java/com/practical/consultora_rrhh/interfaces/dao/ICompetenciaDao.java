package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.interfaces.dao.consultas.IConsultaNombreCodigoDao;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.Competencia;

import java.util.List;

public interface ICompetenciaDao extends IDaoBase<Long, Competencia>, IConsultaNombreCodigoDao,IreactivarEntidad<Competencia> {

    List<Competencia> buscar(String nombre);
    Competencia actualizar(Competencia competencia,String nombre,String descripcion);
}
