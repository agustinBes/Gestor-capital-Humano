package com.practical.consultora_rrhh.interfaces.dao;

import jakarta.persistence.Entity;

public interface IreactivarEntidad<Entity> {
    void reactivar(Entity entity);

}
