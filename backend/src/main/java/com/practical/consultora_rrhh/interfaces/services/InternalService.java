package com.practical.consultora_rrhh.interfaces.services;

/**
 * Metodos basicos para la comunicacion entre servicios
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */

public interface InternalService <Entity,Id>{

    Entity obtenerEntidad(Id id);
    void validarEntidad(Id id);

}
