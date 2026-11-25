package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Cuestionario;
import com.practical.consultora_rrhh.modelo.enums.tipoEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface cuestionarioRepository extends JpaRepository<Cuestionario, Long> , JpaSpecificationExecutor<Cuestionario> {

    List<Cuestionario> findByPuesto_IdAndEstado(Long idPuesto, tipoEstado estado);

    Optional<Cuestionario> findByClaveAndEstadoIn(String clave, List<tipoEstado> estados);

    List<Cuestionario> findAllByCandidato_IdInAndEstadoIn(List<Long> identificadores, List<tipoEstado> tipoEstado);
}
