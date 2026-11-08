package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.excepciones.genericas.IntegridadComprometidaException;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Repository;

@Repository
public interface factorRepository extends IgenericsRepository<Factor, Long>{

    default void integridadCompetencia(Long id, Long idCompetencia) {
        if(!existByIdAndCompetencia_Id(id, idCompetencia)){
            throw new IntegridadComprometidaException("No existe competencia asociada a ese factor");
        }
    }
    boolean existByIdAndCompetencia_Id(Long id, Long idCompetencia);
}
