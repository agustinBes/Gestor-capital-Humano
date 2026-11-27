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
 * Interfaz que hereda de JpaRepository y JpaSpecification obteniendo un conjunto importante de consultas
 * con solo definir el metodo  JpaRepository se encarga de traducir la consulta a codigo sql
 * consultar la bdd y devolver los resultados en el tipo de la entidad.
 * Tambien permite la creacion de consultas dinamicas gracias a JpaSpecification
 * @author Besigniano Agustin
 * @since 1.0
 */
@Repository
public interface preguntaRepository extends JpaRepository<Pregunta,Long>, JpaSpecificationExecutor<Pregunta> {

    boolean existsByOpcionRespuesta_IdAndEliminacionIsFalse(Long id);
    void deleteByPonderaciones(List<Ponderacion> list);

List<Pregunta> findByFactor_Id(Long id);

    List<Pregunta> findByFactor_Competencia_IdIn(List<Long> competencias);
}
