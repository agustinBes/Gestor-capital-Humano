package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.excepciones.especializadas.atributos.NombreDuplicadoException;
import com.practical.consultora_rrhh.excepciones.genericas.DuplicadoException;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Definimos consultas sobre las preguntas para ejecutar creaciones,modificaciones y validaones logicas sobre
 * la disponibilidad de uso de un nombre y de la pregunta en asociada a ese factor y competencia recibidos
 */

@Repository
public interface preguntaRepository extends IgenericsRepository<Pregunta,Long>, JpaSpecificationExecutor<Pregunta> {

    default void existeNombrePorFactorCompetenciaInsensible(String nombre, Long idFactor, Long competencia) {
        if (existsByNombreIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(nombre, idFactor, competencia)) {
            throw new NombreDuplicadoException(" para ese factor y competencia");
        }
    }

    default void existePreguntaPorFactorCompetenciaInsensible(String nombre, Long idFactor, Long competencia) {
        if (existsByPreguntaIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(nombre, idFactor, competencia)) {
            throw new DuplicadoException("Ya existe la pregunta para ese factor y competencia");
        }
    }

    boolean existsByNombreIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(String nombre, Long idFactor, Long idCompetencia);

    boolean existsByPreguntaIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(String nombre, Long idFactor, Long idCompetencia);


    boolean existsByOpcionRespuesta_Id(Long id);
}
