package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
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

    public void existePorNombreInsensible(String nombre) {
    }

    public void existeCodigoInsensible(String codigo) {
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

    }
