package com.practical.consultora_rrhh.interfaces.services;

/**
 /**
 * Métodos básicos para la comunicación entre servicios.
 *
 * <p>Se establecen las siguientes convenciones:
 * <ul>
 *     <li>La comunicación entre Controladores y Servicios se realiza mediante DTOs.</li>
 *     <li>La comunicación entre Servicios se realiza preferentemente mediante Entidades.</li>
 *     <li>La comunicación entre Servicios y DAO se realiza mediante Entidades.</li>
 * </ul>
 *
 * <p>Los servicios deben ofrecer métodos básicos que permitan
 * recuperar y comprobar la existencia de entidades cuando otros
 * servicios necesiten utilizarlas.

  * @author  Besigniano Agustin
 * @since 1.0
 */

public interface InternalService <Entity,Id>{

    Entity obtenerEntidad(Id id);
    void validarEntidad(Id id);
    default void validarEntidadEnUso(Id id){};

}
