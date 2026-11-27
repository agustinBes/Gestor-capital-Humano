package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.interfaces.dao.IcandidatoDao;
import com.practical.consultora_rrhh.interfaces.repositorio.candidatoRepository;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import com.practical.consultora_rrhh.dao.specifications.CandidatoSpecifications;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/**
Implementación de la interfaz DAO para operaciones CRUD y consultas
 * personalizadas sobre la entidad {@link Candidato}.
        *
        * <p>Se abstrae el acceso a datos mediante
 * <a href="https://spring.io/projects/spring-data-jpa#overview">
        * Spring Data JPA</a>, delegando las operaciones de persistencia y busqueda
 * en su correspondiente Repository.</p>
        *
        * @author  Besigniano Agustin
 * @since 1.0
        */
@Repository
public class CandidatoDaoImpl implements IcandidatoDao {
    private final candidatoRepository repository;

    public CandidatoDaoImpl(candidatoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Candidato guardar(Candidato candidato) {
        return repository.save(candidato);
    }

    @Override
    public List<Candidato> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void eliminar(Candidato candidato) {
candidato.setEliminacion(true);
    }

    @Override
    public Optional<Candidato> obtenerPorId(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public List<Candidato> buscar(candidatoFiltrosDto filtro) {
     Specification<Candidato> filtros= CandidatoSpecifications.buscar(filtro);
    return  repository.findAll(filtros);
    }

    @Override
    public boolean existePorId(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
    public List<Candidato> obtenerCandidatosPorId(List<Long> identificadores) {
        return  repository.findAllById(identificadores);
    }
}
