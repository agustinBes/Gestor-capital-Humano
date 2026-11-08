package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import com.practical.consultora_rrhh.interfaces.repositorio.factorRepository;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FactorDaoImpl implements IFactorDao {
    private final factorRepository repository;

    public FactorDaoImpl(factorRepository rep) {
        repository = rep;
    }

    public boolean existePorNombreInsensible(String nombre) {
        return false;
    }

    public boolean existeCodigoInsensible(String codigo) {
        return false;
    }

    public Factor guardar(Factor factor) {
        return null;
    }

    public List<Factor> obtenerTodos() {
        return List.of();
    }

    public void eliminar(Factor factor) {

    }

    public Factor obtenerPorId(Long id) {
        return repository.findByIdOrThrow(id, "el factor");
    }

    public void existePorId(Long aLong) {

    }

    @Override
    public boolean validarCompetencia(Long id, Long idCompetencia) {
        return repository.existByIdAndCompetencia_Id(id, idCompetencia);
    }
}
