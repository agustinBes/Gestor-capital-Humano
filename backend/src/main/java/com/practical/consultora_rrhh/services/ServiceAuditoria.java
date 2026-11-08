package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.response.auditoriaDto;
import com.practical.consultora_rrhh.interfaces.repositorio.auditoriaRepository;
import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAuditoria {
    private final auditoriaRepository repository;
    public ServiceAuditoria(auditoriaRepository auditoriaRepository) {
        repository = auditoriaRepository;}


    public List<auditoriaDto> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(auditoriaDto::new)
                .toList();
    }
    public auditoriaDto crearAuditoria(auditoriaDtoCreate auditoriaDto) {
        Auditoria auditoria = new Auditoria(auditoriaDto);
        Auditoria nueva=repository.save(auditoria);
return  new auditoriaDto(nueva);
    }
}
