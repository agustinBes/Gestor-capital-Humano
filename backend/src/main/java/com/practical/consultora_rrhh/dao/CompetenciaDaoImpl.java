package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.ICompetenciaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.competenciaRepository;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.dao.specifications.TextoSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
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
        Specification<Competencia> filtro= TextoSpecifications.coincidencia("nombre",nombre,true,false);
        return  repository.findAll(filtro);

    }

    public boolean existePorNombreInsensible(String nombre) {
        Specification<Competencia> filtro= TextoSpecifications.coincidencia("nombre",nombre,false,true);
      return  repository.exists(filtro);
    }

    public boolean existePorCodigoInsensible(String codigo) {
      Specification<Competencia> filtro= TextoSpecifications.coincidencia("codigo",codigo,false,true);
return  repository.exists(filtro);
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
    @Transactional
    public void reactivar(Competencia c){
        c.setEliminacion(false);
    }

    public Optional<Competencia> obtenerPorId(Long id) {
        return repository.findById(id);}

    public boolean existePorId(Long id) {
return repository.existsById(id);
    }
}
