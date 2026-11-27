package com.practical.consultora_rrhh.interfaces.genericos;

import java.util.List;
import java.util.Optional;
/**
 * Define metodos basicos de consultas a la base de datos
 * a las que deben dar respuesta para que el servicio pueda
 * validar y gestionar la informacion
 *
 * @param <Id> define el identificador primario de la entidad sea simple o compuesto
 * @param <Entity> define la clase de la entidad
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface IDaoBase<Id, Entity> {

    Entity guardar(Entity entity);

    List<Entity> obtenerTodos();

    void eliminar(Entity entity);

   Optional<Entity> obtenerPorId(Id id);

    boolean existePorId(Id id);
}