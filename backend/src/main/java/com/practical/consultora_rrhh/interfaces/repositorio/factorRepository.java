package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface factorRepository extends IgenericsRepository<Factor, Long>{

      boolean existsByIdAndCompetencia_Id(Long id, Long idCompetencia);

      List<Factor>  findByNombreIgnoreCaseAndCompetencia_NombreIgnoreCase(String nombre,String Competencia);
      List<Factor> findByNombreIgnoreCase(String nombre);
      List<Factor> findByCompetencia_NombreIgnoreCase(String Competencia);
}
