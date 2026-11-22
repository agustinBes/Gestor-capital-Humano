package com.practical.consultora_rrhh.dto.request;

import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record auditoriaDtoCreate(
       Long id,
       @NotNull(message = "se requiere el id de la entidad eliminada")
       Long id_entidad,
       @NotBlank(message = "se requiere el nombre del registro eliminado")
        String registro,
        @NotBlank
       tipoAuditoria tipoAuditoria,
        @NotBlank
        LocalDateTime fecha,
       //en espera de implementacion
       Long idConsultor
) {
}
