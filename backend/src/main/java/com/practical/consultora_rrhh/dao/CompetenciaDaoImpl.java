package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.ICompetenciaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.competenciaRepository;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompetenciaDaoImpl implements ICompetenciaDao {
    private final competenciaRepository repository;

    public CompetenciaDaoImpl(competenciaRepository repository) {
        this.repository = repository;
    }

    public List<Competencia> obtenerPorNombreInsensible(String nombre) {
        return repository.findByNombreIgnoreCaseContaining(nombre);
    }

    public boolean existePorNombreInsensible(String nombre) {
      return  repository.existsByNombreIgnoreCase(nombre);
    }

    public boolean existePorCodigoInsensible(String codigo) {

      return  repository.existsByCodigo(codigo);
    }

    public Competencia guardar(Competencia competencia) {
        return null;
    }

    public List<Competencia> obtenerTodos() {
        return repository.findAll();
    }

    public void eliminar(Competencia competencia) {

    }

    public Optional<Competencia> obtenerPorId(Long id) {
        return repository.findById(id);}

    public boolean existePorId(Long id) {
return true;
    }
}
