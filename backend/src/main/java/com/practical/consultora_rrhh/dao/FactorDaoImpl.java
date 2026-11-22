package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import com.practical.consultora_rrhh.interfaces.repositorio.factorRepository;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import com.practical.consultora_rrhh.modelo.specifications.FactorSpecifications;
import com.practical.consultora_rrhh.modelo.specifications.TextoSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Factor> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public boolean existePorId(Long id) {
return  repository.existsById(id);
    }

    public boolean validarCompetencia(Long id, Long idCompetencia) {
        return repository.existsByIdAndCompetencia_Id(id, idCompetencia);
    }

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
@Override

public boolean existePorCodigoInsensible(String codigo) {
    Specification<Factor> filtro= TextoSpecifications.coincidencia("codigo",codigo,false,true);
    return repository.exists(filtro);
}
@Override
public boolean existeNroOrdenPorCompetencia(short id, Long idCompetencia) {
return  repository.existsByOrdenAndCompetencia_IdAndEliminacionIsFalse(id,idCompetencia);
}
@Override
public boolean existeCompetenciaEnUso(Long idCompetencia) {
        return repository.existsByCompetencia_IdAndEliminacionIsFalse(idCompetencia);
    }
}
