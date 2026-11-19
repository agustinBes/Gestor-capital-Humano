package com.practical.consultora_rrhh.interfaces.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface codigoRepository<Entity, Id> extends JpaRepository<Entity, Id> {

    boolean existsByNombreIgnoreCase(String nombre);
    boolean existsByCodigoIgnoreCase(String codigo);


}
