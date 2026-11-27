package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.interfaces.auxiliares.IcompetenciaEnUso;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import com.practical.consultora_rrhh.modelo.id.PuestoCompetenciaId;

import java.util.List;
/**
 * Interfaz especializa de la interfaz con operaciones genericas IDaoBase {@link IDaoBase}
 * tales como guardar,eliminar,listar, obtenr por cierto criterio de busqueda o campo
 * Agregamos los metodos especificados de la entidad que se requieran
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface IpuestoCompetenciaDao extends IDaoBase<PuestoCompetenciaId, PuestoCompetencia>, IcompetenciaEnUso {

void eliminarPorPuesto(Long idPuesto);
List<PuestoCompetencia> obtenerPorCompetencia(Long id);
List<PuestoCompetencia> obtenerPorPuesto(Long id);
boolean existePuestoCompetencia(Long idPuesto,Long idCompetencia);
PuestoCompetencia actualizarPonderacion(PuestoCompetencia pc,short ponderacion);
List<PuestoCompetencia> actualizarPuestoCompetencia(Long idPuesto,List<PuestoCompetencia> pc);


    List<PuestoCompetencia> obtenerPorPuestos(List<Long> coleccion);
}
