package com.practical.consultora_rrhh.interfaces.dao;

import java.util.List;
import java.util.Optional;

public interface IDaoBase<Id, Entity> {

    Entity guardar(Entity entity);

    List<Entity> obtenerTodos();

    void eliminar(Entity entity);

   Optional<Entity> obtenerPorId(Id id);

    boolean existePorId(Id id);
}