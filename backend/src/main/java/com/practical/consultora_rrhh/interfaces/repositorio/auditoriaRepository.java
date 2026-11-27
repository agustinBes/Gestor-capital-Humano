package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Interfaz que hereda de JpaRepository obteniendo un conjunto importante de consultas
 * con solo definir el metodo  JpaRepository se encarga de traducir la consulta a codigo sql
 * consultar la bdd y devolver los resultados en el tipo de la entidad
 * @author Besigniano Agustin
 * @since 1.0
 */
@Repository
public interface auditoriaRepository extends JpaRepository<Auditoria,Long> {

    Optional<Auditoria> findByIdEntidadAndRegistroEliminadoAndAuditoria(Long id, String nombre, tipoAuditoria tipoAuditoria);
}
