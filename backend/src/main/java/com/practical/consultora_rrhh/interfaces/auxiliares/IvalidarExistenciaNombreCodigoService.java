package com.practical.consultora_rrhh.interfaces.auxiliares;

/**
 * Contrato para las entidades que deben validar por nombre y por codigo
 * durante su creacion,modificacion o hasta en filtros de busqueda
 * Puesto,Competencia,Factor
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface IvalidarExistenciaNombreCodigoService {
    void existePorNombreInsensible(String nombre);
    void existeCodigoInsensible(String codigo);
}
