package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.interfaces.services.IcompetenciaEnUso;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import com.practical.consultora_rrhh.modelo.id.PuestoCompetenciaId;

import java.util.List;

public interface IpuestoCompetenciaDao extends  IDaoBase<PuestoCompetenciaId, PuestoCompetencia>, IcompetenciaEnUso {

void eliminarPorPuesto(Long idPuesto);
List<PuestoCompetencia> obtenerPorCompetencia(Long id);
List<PuestoCompetencia> obtenerPorPuesto(Long id);
}
