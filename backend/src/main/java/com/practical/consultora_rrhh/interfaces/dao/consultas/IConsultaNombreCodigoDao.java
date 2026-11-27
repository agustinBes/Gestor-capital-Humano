package com.practical.consultora_rrhh.interfaces.dao.consultas;
/**
 * Consultas especificas de ciertas entidades que deben implementarse
 */
public interface IConsultaNombreCodigoDao {
    boolean existePorNombreInsensible(String nombre);
    boolean existePorCodigoInsensible(String codigo);

}
