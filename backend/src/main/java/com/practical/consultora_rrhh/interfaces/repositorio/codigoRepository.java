package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CodigoDuplicadoException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface codigoRepository<Entity, Id> extends IgenericsRepository<Entity, Id>{

    boolean existsByCodigo(String codigo);
default boolean existePorCodigoOrThrow(String codigo){
    if(existsByCodigo(codigo)){
        throw new CodigoDuplicadoException("Ya existe el codigo: "+codigo+"  en el sistema");
    }
    return false;
}

}
