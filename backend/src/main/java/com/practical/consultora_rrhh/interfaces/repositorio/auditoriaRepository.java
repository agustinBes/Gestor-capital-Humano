package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface auditoriaRepository extends JpaRepository<Auditoria,Long> {

    Optional<Auditoria> findByIdEntidadAndRegistroEliminadoAndAuditoria(Long id, String nombre, tipoAuditoria tipoAuditoria);
}
