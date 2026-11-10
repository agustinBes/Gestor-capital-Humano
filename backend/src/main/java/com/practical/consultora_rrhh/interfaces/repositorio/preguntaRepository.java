package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.excepciones.especializadas.atributos.NombreDuplicadoException;
import com.practical.consultora_rrhh.excepciones.genericas.DuplicadoException;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Definimos consultas sobre las preguntas para ejecutar creaciones,modificaciones y validaones logicas sobre
 * la disponibilidad de uso de un nombre y de la pregunta en asociada a ese factor y competencia recibidos
 */

@Repository
public interface preguntaRepository extends JpaRepository<Pregunta,Long>, JpaSpecificationExecutor<Pregunta> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(String nombre, Long idFactor, Long idCompetencia);

    boolean existsByPreguntaIgnoreCaseAndFactor_IdAndFactor_Competencia_Id(String nombre, Long idFactor, Long idCompetencia);


    boolean existsByOpcionRespuesta_Id(Long id);

List<Pregunta> findByFactor_Id(Long id);
}
