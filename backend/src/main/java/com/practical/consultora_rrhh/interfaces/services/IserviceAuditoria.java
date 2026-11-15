package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.response.auditoriaDto;

import java.util.List;

public interface IserviceAuditoria {
    public List<auditoriaDto> obtenerTodos();
    public auditoriaDto crearAuditoria(auditoriaDtoCreate dto);
}
