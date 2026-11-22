package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.dto.autenticacion.usuarioDtoAuth;
import com.practical.consultora_rrhh.dto.autenticacion.usuarioCandidatoDtoAuth;
import com.practical.consultora_rrhh.dto.autenticacion.usuarioConsultorDtoAuth;
import com.practical.consultora_rrhh.interfaces.auxiliares.auth.Iautenticacion;
import com.practical.consultora_rrhh.interfaces.auxiliares.auth.Iautenticador;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/login")
public class ControllerLogin implements Iautenticacion {
private final Iautenticador autenticador;
public ControllerLogin(Iautenticador autenticador) {
    this.autenticador = autenticador;
}
    @Override
    @PostMapping("/candidato/auth")
    public ResponseEntity<usuarioDtoAuth> autenticarUsuario( @RequestBody usuarioCandidatoDtoAuth dto) {
    usuarioDtoAuth aux= autenticador.autenticarCandidato(dto);
        return ResponseEntity.ok(aux);
    }

    @Override()
    @PostMapping("/consultor/auth")
    public ResponseEntity<usuarioDtoAuth> autenticarUsuario(@RequestBody usuarioConsultorDtoAuth dto) {
     usuarioDtoAuth aux=autenticador.autenticarConsultor(dto);
        return ResponseEntity.ok(aux);
    }
}
