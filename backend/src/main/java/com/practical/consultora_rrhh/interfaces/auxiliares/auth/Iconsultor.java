package com.practical.consultora_rrhh.interfaces.auxiliares.auth;

import com.practical.consultora_rrhh.modelo.clases.Consultor;

public interface Iconsultor {

    Consultor obtenerConsultor(Long id);
    Consultor obtenerConsultor(String usuario);
}
