package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Ponderacion;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Definimos consultas sobre las preguntas para ejecutar creaciones,modificaciones y validaones logicas sobre
 * la disponibilidad de uso de un nombre y de la pregunta en asociada a ese factor y competencia recibidos
 */

@Repository
public interface preguntaRepository extends JpaRepository<Pregunta,Long>, JpaSpecificationExecutor<Pregunta> {

    boolean existsByOpcionRespuesta_IdAndEliminacionIsFalse(Long id);
    void deleteByPonderaciones(List<Ponderacion> list);

List<Pregunta> findByFactor_Id(Long id);

    List<Pregunta> findByFactor_Competencia_IdIn(List<Long> competencias);
}
