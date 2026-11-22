package com.practical.consultora_rrhh.interfaces.auxiliares.auth;

import com.practical.consultora_rrhh.dto.autenticacion.usuarioCandidatoDtoAuth;
import com.practical.consultora_rrhh.dto.autenticacion.usuarioConsultorDtoAuth;
import com.practical.consultora_rrhh.dto.autenticacion.usuarioDtoAuth;
import org.springframework.http.ResponseEntity;

public interface Iautenticacion{
    ResponseEntity<usuarioDtoAuth> autenticarUsuario(usuarioCandidatoDtoAuth dto);
    ResponseEntity<usuarioDtoAuth> autenticarUsuario(usuarioConsultorDtoAuth dto);

}
