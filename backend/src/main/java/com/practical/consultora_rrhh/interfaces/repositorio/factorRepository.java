package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface factorRepository extends JpaRepository<Factor, Long> {

      boolean existsByIdAndCompetencia_Id(Long id, Long idCompetencia);
boolean existsByOrdenAndCompetencia_Id(short id, Long idCompetencia);

      boolean existsById(Long id);

      boolean existsByNombreIgnoreCase(String nombre);
      boolean existsByCodigoIgnoreCase(String codigo);

      List<Factor>  findByNombreIgnoreCaseAndCompetencia_NombreIgnoreCase(String nombre,String Competencia);
      List<Factor> findByNombreIgnoreCase(String nombre);
      List<Factor> findByCompetencia_NombreIgnoreCase(String Competencia);
}
