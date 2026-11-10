package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CodigoDuplicadoException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface codigoRepository<Entity, Id> extends JpaRepository<Entity, Id> {
    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByCodigo(String codigo);
    boolean existsByDescripcion(String descripcion);

}
