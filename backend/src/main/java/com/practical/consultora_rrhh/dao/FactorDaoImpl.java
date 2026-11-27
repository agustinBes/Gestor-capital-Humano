package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import com.practical.consultora_rrhh.interfaces.repositorio.factorRepository;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import com.practical.consultora_rrhh.dao.specifications.FactorSpecifications;
import com.practical.consultora_rrhh.dao.specifications.TextoSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 Implementación de la interfaz DAO para operaciones CRUD y consultas
 * personalizadas sobre la entidad {@link Factor}.
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
public class FactorDaoImpl implements IFactorDao {
    private final factorRepository repository;

    public FactorDaoImpl(factorRepository rep) {
        repository = rep;
    }


    public Factor guardar(Factor factor) {
        return repository.save(factor);
    }

    @Override
    @Transactional
    public Factor actualizar(Factor factor, Map<String, String> texto, Competencia c, Short nroOrden) {
if(texto!=null){
    if(texto.containsKey("nombre")){
        factor.setNombre(texto.get("nombre"));}
    if(texto.containsKey("descripcion")){
        factor.setDescripcion(texto.get("descripcion"));
    }

    }
if(c!=null){factor.setCompetencia(c);}
if(nroOrden!=null){factor.setOrden(nroOrden);}

return factor;}




    public List<Factor> obtenerTodos() {
        return repository.findAll();
    }
@Transactional
    public void eliminar(Factor factor) {
        factor.setEliminacion(true);
    }
    @Transactional
    public void reactivar(Factor factor) {
        factor.setEliminacion(false);
    }

    public Optional<Factor> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public boolean existePorId(Long id) {
return  repository.existsById(id);
    }

    public boolean validarCompetencia(Long id, Long idCompetencia) {
        return repository.existsByIdAndCompetencia_Id(id, idCompetencia);
    }

    /**
     * Busqueda usando la interfaz especification para consultas dinamicas entre el factor y la competencia
     * {@link Factor} y {@link Competencia}
     */

    @Override
    public List<Factor> buscar(String factor, String competencia) {

        Specification<Factor> filtro= FactorSpecifications.buscar(factor, competencia);

return         repository.findAll(filtro);
    }
//----------------------------------------------------------------------------
    @Override

public boolean existePorNombreInsensible(String nombre) {
        Specification<Factor> filtro = TextoSpecifications.coincidencia("nombre", nombre,false,true);
        return repository.exists(filtro);

   }
    /**
     * Busqueda a traves del codigo ignorando mayusculas, ignorando espacios extra
     * Ignorando los acentos que pudiera contener
     */
@Override
public boolean existePorCodigoInsensible(String codigo) {
    Specification<Factor> filtro= TextoSpecifications.coincidencia("codigo",codigo,false,true);
    return repository.exists(filtro);
}

    /** Consultar la disponibilidad del nro de orden asociado
     *  a una competencia
     *  Solo se considera entidades que no han sido eliminadas
     */
    @Override
public boolean existeNroOrdenPorCompetencia(short id, Long idCompetencia) {
return  repository.existsByOrdenAndCompetencia_IdAndEliminacionIsFalse(id,idCompetencia);
}

    /**
     * Validar si la competencia esta siendo usada en el sistema
     */
    @Override
public boolean existeCompetenciaEnUso(Long idCompetencia) {
        return repository.existsByCompetencia_IdAndEliminacionIsFalse(idCompetencia);
    }
}
