package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.dto.response.opcionDto;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;

import java.util.List;
/**
 * Interfaz especializa de la interfaz con operaciones genericas IDaoBase {@link IDaoBase}
 * tales como guardar,eliminar,listar, obtenr por cierto criterio de busqueda o campo
 * Agregamos los metodos especificados de la entidad que se requieran
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface IOpcionRespuestaDao extends IDaoBase<Long, OpcionRespuesta>,IreactivarEntidad<OpcionRespuesta>{
    OpcionRespuesta actualizar(String descripcion,OpcionRespuesta opcionRespuesta,List<opcionDto> opciones);
    List<OpcionRespuesta> obtenerPorNombreParcial(String texto);
    boolean existePorNombreInsensible(String nombre);
}
