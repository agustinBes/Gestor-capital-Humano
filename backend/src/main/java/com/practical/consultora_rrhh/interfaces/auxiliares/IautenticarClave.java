package com.practical.consultora_rrhh.interfaces.auxiliares;

import com.practical.consultora_rrhh.modelo.clases.Cuestionario;

import java.util.List;
import java.util.Optional;

public interface IautenticarClave {

    Optional<Cuestionario> obtenerPorClave(String clave);
    List<String> obtenerClaves();
}
