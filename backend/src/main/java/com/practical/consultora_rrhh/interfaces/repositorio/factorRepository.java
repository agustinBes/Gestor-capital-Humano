package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface factorRepository extends codigoRepository<Factor, Long> {

      boolean existsByIdAndCompetencia_Id(Long id, Long idCompetencia);
boolean existsByOrdenAndCompetencia_Id(short id, Long idCompetencia);
  boolean   existsByCompetencia_IdAndEliminacionIsFalse(Long idCompetencia);

      boolean existsById(Long id);


    @Query("SELECT f FROM Factor f JOIN f.competencia c " +
            "WHERE LOWER(function('unaccent', f.nombre)) = LOWER(function('unaccent', :nombre)) " +
            "AND LOWER(function('unaccent', c.nombre)) = LOWER(function('unaccent', :competencia))")
    List<Factor> findByNombreIgnoreCaseAndCompetencia_NombreIgnoreCase(
            @Param("nombre") String nombre,
            @Param("competencia") String competencia);



    @Query("SELECT f FROM Factor f " +
            "WHERE LOWER(function('unaccent', f.nombre)) = LOWER(function('unaccent', :nombre))")
    List<Factor> findByNombreIgnoreCase(@Param("nombre") String nombre);

    @Query("SELECT f FROM Factor f JOIN f.competencia c " +
            "WHERE LOWER(function('unaccent', c.nombre)) = LOWER(function('unaccent', :competencia))")
    List<Factor> findByCompetencia_NombreIgnoreCase(@Param("competencia") String competencia);

}
