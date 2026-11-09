package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Competencia;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface competenciaRepository extends codigoRepository<Competencia,Long>{
List<Competencia>  findByNombreIgnoreCaseContaining(String nombre);
}
