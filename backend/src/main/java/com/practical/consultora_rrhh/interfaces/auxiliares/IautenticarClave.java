package com.practical.consultora_rrhh.interfaces.auxiliares;

import com.practical.consultora_rrhh.dto.request.candidatoDtoAuth;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;

import java.util.List;
import java.util.Optional;

public interface IautenticarClave {

    Optional<Cuestionario> obtenerPorClave(String clave);
    Optional<Cuestionario> obtenerPorClave(candidatoDtoAuth dto);
    List<String> obtenerClaves();
}
