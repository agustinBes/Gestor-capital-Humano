package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.interfaces.auxiliares.IautenticarClave;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;

import java.util.List;
/**
 * Interfaz especializa de la interfaz con operaciones genericas IDaoBase {@link IDaoBase}
 * tales como guardar,eliminar,listar, obtenr por cierto criterio de busqueda o campo
 * Agregamos los metodos especificados de la entidad que se requieran
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface IcuestionarioDao extends IDaoBase<Long, Cuestionario> , IautenticarClave {


List<Cuestionario> obtenerCompletadosPorPuesto(Long idPuesto);

List<Cuestionario> guardarTodos(List<Cuestionario> cuestionarios);
    List<Cuestionario> cuestionariosActivos(List<Long> identificadores);

    List<Cuestionario> buscarPorPuesto(List<Long> puestos);

    List<Cuestionario> buscarXPuesto(Long id);

    List<Cuestionario> buscarxIds(List<Long> cuestionarios);
}
