package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface opcionRespuestaRepository extends JpaRepository<OpcionRespuesta, Long> {
 List<OpcionRespuesta>  findByNombreContaining(String id);
boolean existsByNombreIgnoreCase(String nombre);
}
