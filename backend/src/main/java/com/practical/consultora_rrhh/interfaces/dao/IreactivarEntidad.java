package com.practical.consultora_rrhh.interfaces.dao;

import jakarta.persistence.Entity;

/**
 * Mecanismo pensado para reactivar Entidades en el sistema que sufrieron una eliminacion logica
 * @author Besigniano Agustin
 * @since 1.0
 */
public interface IreactivarEntidad<Entity> {
    void reactivar(Entity entity);

}
