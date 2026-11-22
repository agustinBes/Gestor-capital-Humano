package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.response.auditoriaDto;
import com.practical.consultora_rrhh.excepciones.genericas.IntegridadComprometidaException;
import com.practical.consultora_rrhh.interfaces.auxiliares.auth.Iconsultor;
import com.practical.consultora_rrhh.interfaces.repositorio.auditoriaRepository;
import com.practical.consultora_rrhh.interfaces.services.IserviceAuditoria;
import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import com.practical.consultora_rrhh.modelo.clases.Consultor;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceAuditoria implements IserviceAuditoria {
    private final auditoriaRepository repository;
    private final Iconsultor consultor;

    public ServiceAuditoria(auditoriaRepository auditoriaRepository, Iconsultor consultor) {
        repository = auditoriaRepository;
        this.consultor = consultor;
    }

    @Override
    public List<auditoriaDto> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(auditoriaDto::new)
                .toList();
    }

    @Override
    public auditoriaDto crearAuditoria(auditoriaDtoCreate auditoriaDto) {
        Consultor responsable = consultor.obtenerConsultor(auditoriaDto.idConsultor());

        Auditoria auditoria = new Auditoria(auditoriaDto, responsable);
        Auditoria nueva = repository.save(auditoria);
        return new auditoriaDto(nueva);
    }

    public String buscarAuditor(Long id, String nombre, tipoAuditoria tipoAuditoria) {
        Optional<Auditoria> buscada = repository.findByIdEntidadAndRegistroEliminadoAndAuditoria(id,nombre,tipoAuditoria);
        Auditoria auditor = buscada.orElseThrow(() -> new IntegridadComprometidaException("Conflicto en la integridad referencial de la bdd"));
return  auditor.getResponsible().getApellido()+", "+ auditor.getResponsible().getNombre();    }
}