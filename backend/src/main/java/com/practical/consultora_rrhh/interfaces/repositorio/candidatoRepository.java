package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Candidato;
import com.practical.consultora_rrhh.modelo.enums.tipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface candidatoRepository extends JpaRepository<Candidato,Long>, JpaSpecificationExecutor<Candidato> {


    Optional<Candidato> findByTipoAndDocumento(tipoDocumento tipo, String documento);

    long countByIdIn(List<Long> ids);
}
