package com.practical.consultora_rrhh.interfaces.auxiliares.auth;

import com.practical.consultora_rrhh.dto.autenticacion.usuarioCandidatoDtoAuth;
import com.practical.consultora_rrhh.dto.autenticacion.usuarioConsultorDtoAuth;
import com.practical.consultora_rrhh.dto.autenticacion.usuarioDtoAuth;

public interface Iautenticador {

    usuarioDtoAuth autenticarConsultor(usuarioConsultorDtoAuth dto);
    usuarioDtoAuth autenticarCandidato(usuarioCandidatoDtoAuth dto);
}
