package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import java.time.LocalDateTime;

/**
 * Formato de datos para presentar las auditorias creadas
 */
public record auditoriaDto(
        Long id,
        Long id_entidad,
        String registro,
        String tipo,
        LocalDateTime fecha,
       String consultor
) {
    public auditoriaDto(Auditoria x){
        this(
                x.getId(),
                x.getId(),
                x.getRegistroEliminado(),
                tipoAuditoria.desdeTipo(x.getAuditoria()),
            x.getFecha(),
        x.getResponsible().getNombre()+" "+x.getResponsible().getApellido()
        );
    }

    }
