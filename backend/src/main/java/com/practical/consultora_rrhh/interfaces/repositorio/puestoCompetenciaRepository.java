package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import com.practical.consultora_rrhh.modelo.id.PuestoCompetenciaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Interfaz que hereda de JpaRepository obteniendo un conjunto importante de consultas
 * con solo definir el metodo  JpaRepository se encarga de traducir la consulta a codigo sql
 * consultar la bdd y devolver los resultados en el tipo de la entidad.
  * @author Besigniano Agustin
 * @since 1.0
 */
@Repository
public interface puestoCompetenciaRepository extends JpaRepository<PuestoCompetencia, PuestoCompetenciaId> {

    boolean existsByCompetencia_Id(Long competenciaId);
    List<PuestoCompetencia> findByCompetencia_Id(Long competenciaId);
    List<PuestoCompetencia> findByPuesto_Id(Long id);
    List<PuestoCompetencia> findByPuesto_IdIn(List<Long> ids);
    boolean existsByCompetencia_IdAndPuesto_Id(Long idCompetencia,Long idPuestoId);
}