package com.practical.consultora_rrhh.interfaces.dao.consultas;

public interface IConsultaNombreCodigoDao {
    boolean existePorNombreInsensible(String nombre);
    boolean existePorCodigoInsensible(String codigo);

}
