package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.ICompetenciaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.competenciaRepository;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.dao.specifications.TextoSpecifications;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
Implementación de la interfaz DAO para operaciones CRUD y consultas
 * personalizadas sobre la entidad {@link Competencia}.
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
public class CompetenciaDaoImpl implements ICompetenciaDao {
    private final competenciaRepository repository;

    public CompetenciaDaoImpl(competenciaRepository repository) {
        this.repository = repository;
    }
    /**
     * Busqueda a traves del nombre ignorando mayusculas, ignorando espacios extra
     * Ignorando los acentos que pudiera contener
     * Sin Considerar tuplas eliminadas en el proceso
     * Buscando coincidencias parciales
     */
    public List<Competencia> buscar(String nombre) {
        Specification<Competencia> filtro= TextoSpecifications.coincidencia("nombre",nombre,true,false);
        return  repository.findAll(filtro);
    }

    /**
     * Busqueda a traves del nombre ignorando mayusculas, ignorando espacios extra
     * Ignorando los acentos que pudiera contener
     * Considerando tuplas eliminadas en el proceso
     */
    public boolean existePorNombreInsensible(String nombre) {
        Specification<Competencia> filtro= TextoSpecifications.coincidencia("nombre",nombre,false,true);
      return  repository.exists(filtro);
    }
    /**
     * Busqueda a traves del codigo ignorando mayusculas, ignorando espacios extra
     * Ignorando los acentos que pudiera contener
     * Consideramos los codigos de factores eliminados tambien
     */

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
