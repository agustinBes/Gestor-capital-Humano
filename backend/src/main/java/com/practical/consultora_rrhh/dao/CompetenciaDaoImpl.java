package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.ICompetenciaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.competenciaRepository;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompetenciaDaoImpl implements ICompetenciaDao {
    private final competenciaRepository repository;

    public CompetenciaDaoImpl(competenciaRepository repository) {
        this.repository = repository;
    }

    public List<Competencia> buscar(String nombre) {
        return repository.findByNombreIgnoreCaseContaining(nombre);
    }

    public boolean existePorNombreInsensible(String nombre) {
      return  repository.existsByNombreIgnoreCase(nombre);
    }

    public boolean existePorCodigoInsensible(String codigo) {
      return  repository.existsByCodigoIgnoreCase(codigo);
    }

    public Competencia guardar(Competencia competencia) {
        return repository.save(competencia);}

    @Override
    @Transactional
    public Competencia actualizar(Competencia competencia, String nombre, String descripcion) {
        if(nombre!=null){
            competencia.setNombre(nombre);
        }
        if(descripcion!=null){
            competencia.setDescripcion(descripcion);
        }
    return competencia;
    }

    public List<Competencia> obtenerTodos() {
        return repository.findAll();
    }

@Transactional
    public void eliminar(Competencia competencia) {
        competencia.setEliminacion(true);
    }

    public Optional<Competencia> obtenerPorId(Long id) {
        return repository.findById(id);}

    public boolean existePorId(Long id) {
return repository.existsById(id);
    }
}
