package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import com.practical.consultora_rrhh.modelo.id.PuestoCompetenciaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface puestoCompetenciaRepository extends JpaRepository<PuestoCompetencia, PuestoCompetenciaId> {

    boolean existsByCompetencia_IdAndEliminacionIsFalse(Long competenciaId);
    List<PuestoCompetencia> findByCompetencia_Id(Long competenciaId);
    List<PuestoCompetencia> findByPuesto_Id(Long id);
}