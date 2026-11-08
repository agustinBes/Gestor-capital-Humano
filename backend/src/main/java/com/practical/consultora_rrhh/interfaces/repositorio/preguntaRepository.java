package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.excepciones.especializadas.atributos.NombreDuplicadoException;
import com.practical.consultora_rrhh.excepciones.genericas.DuplicadoException;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import org.springframework.stereotype.Repository;

@Repository
public interface preguntaRepository extends IgenericsRepository<Pregunta,Long> {

    default boolean existeNombrePorFactorCompetenciaInsensible(String nombre,Long idFactor,Long competencia){
        if(existsByNombreIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(nombre,  idFactor, competencia)){
            throw new NombreDuplicadoException(" para ese factor y competencia");
        }

        return false;
    }

    default boolean existePreguntaPorFactorCompetenciaInsensible(String nombre,Long idFactor,Long competencia){
        if(existsByPreguntaIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(nombre,  idFactor, competencia)){
            throw new DuplicadoException("Ya existe la pregunta para ese factor y competencia");
        }

        return false;
    }
boolean existsByNombreIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(String nombre, Long idFactor, Long idCompetencia);

    boolean existsByPreguntaIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(String nombre, Long idFactor, Long idCompetencia);


boolean existsByOpcionRespuesta_Id(Long id);

}