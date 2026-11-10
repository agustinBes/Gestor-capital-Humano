package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import com.practical.consultora_rrhh.interfaces.repositorio.factorRepository;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import jakarta.transaction.Transactional;
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
    public List<Factor> buscar(String factor,String competencia) {
        if(factor!=null && competencia!=null) {
            return repository.findByNombreIgnoreCaseAndCompetencia_NombreIgnoreCase(factor, competencia);
        }
        else {
            if (factor != null) {
                return repository.findByNombreIgnoreCase(factor);
            }
        }
        return repository.findByCompetencia_NombreIgnoreCase(competencia);

        }
//----------------------------------------------------------------------------
    @Override

public boolean existePorNombreInsensible(String nombre) {

        return repository.existsByNombreIgnoreCase(nombre);
}
@Override

public boolean existePorCodigoInsensible(String codigo) {

        return   repository.existsByCodigoIgnoreCase(codigo);
}
@Override
public boolean existeNroOrdenPorCompetencia(short id, Long idCompetencia) {
return  repository.existsByOrdenAndCompetencia_Id(id,idCompetencia);
}



}
