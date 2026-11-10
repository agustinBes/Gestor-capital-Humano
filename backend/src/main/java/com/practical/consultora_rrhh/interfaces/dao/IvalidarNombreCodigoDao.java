package com.practical.consultora_rrhh.interfaces.dao;

public interface IvalidarNombreCodigoDao {
    boolean existePorNombreInsensible(String nombre);
    boolean existePorCodigoInsensible(String codigo);

}
