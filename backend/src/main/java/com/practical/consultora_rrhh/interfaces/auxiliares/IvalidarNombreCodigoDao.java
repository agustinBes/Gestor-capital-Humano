package com.practical.consultora_rrhh.interfaces.auxiliares;

public interface IvalidarNombreCodigoDao {
    void existePorNombreInsensible(String nombre);
    void existeCodigoInsensible(String codigo);

}
