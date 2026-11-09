package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import com.practical.consultora_rrhh.validaciones.FactorValidators;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceFactor implements IserviceFactor {
private final IFactorDao dao;
private final IserviceCompetencia serviceCompetencia;
private final FactorValidators validators;
public ServiceFactor(IFactorDao dao, IserviceCompetencia service, FactorValidators validators) {
        this.dao = dao;
        this.serviceCompetencia = service;
    this.validators = validators;
}

    @Override
    public factorDto guardar(factorDtoCreate dto) {
        return null;
    }

    @Override
    public factorDto actualizar(Long aLong, factorDtoCreate dto) {
        return null;
    }

    @Override
    public List<factorDto> obtenerTodos() {
        return List.of();
    }

    @Override
    public void eliminar(Long aLong) {

    }

    @Override
    public factorDto obtenerPorId(Long aLong) {
        return null;
    }

    public boolean validarExistencia(Long id) {
        return false;
    }

    public void existePorNombreInsensible(String nombre) {

    }

    public void existeCodigoInsensible(String codigo) {

    }

    public Factor obtenerEntidad(Long id) {
    return dao.obtenerPorId(id);
    }

    public void validarConsistencia(Long id, Long idCompetencia) {
         dao.validarCompetencia(id,idCompetencia);
    }

    public void validarEntidad(Long aLong) {

    }

    public List<factorDto> buscar(factorFiltrosDto dto) {
    validators.validarCriterios(dto);

    return dao.buscar(dto.factor(), dto.competencia())
                .stream().map(factorDto::new)
                .toList();


}

}
