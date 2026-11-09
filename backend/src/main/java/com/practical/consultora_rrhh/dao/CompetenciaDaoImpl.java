package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.ICompetenciaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.competenciaRepository;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompetenciaDaoImpl implements ICompetenciaDao {
    private final competenciaRepository repository;

    public CompetenciaDaoImpl(competenciaRepository repository) {
        this.repository = repository;
    }

    public List<Competencia> obtenerPorNombreInsensible(String nombre) {
        return repository.findByNombreIgnoreCaseContaining(nombre);
    }

    public void existePorNombreInsensible(String nombre) {
      repository.existePorNombreOrThrow(nombre);
    }

    public void existeCodigoInsensible(String codigo) {
        repository.existePorCodigoOrThrow(codigo);
    }

    public Competencia guardar(Competencia competencia) {
        return null;
    }

    public List<Competencia> obtenerTodos() {
        return repository.findAll();
    }

    public void eliminar(Competencia competencia) {

    }

    public Competencia obtenerPorId(Long aLong) {
        return null;
    }

    public void existePorId(Long aLong) {

    }
}
