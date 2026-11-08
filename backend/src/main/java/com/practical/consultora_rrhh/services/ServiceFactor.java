package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceFactor implements IserviceFactor {
private final IFactorDao dao;
private final IserviceCompetencia serviceCompetencia;
public ServiceFactor(IFactorDao dao, IserviceCompetencia service) {
        this.dao = dao;
        this.serviceCompetencia = service;
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

    @Override
    public void existePorNombreInsensible(String nombre) {

    }

    @Override
    public void existeCodigoInsensible(String codigo) {

    }

    @Override
    public Factor obtenerEntidad(Long id) {
    return dao.obtenerPorId(id);
    }

    @Override
    public void validarConsistencia(Long id, Long idCompetencia) {
         dao.validarCompetencia(id,idCompetencia);
    }

    @Override
    public void validarEntidad(Long aLong) {

    }

    @Override
    public List<factorDto> obtenerPorNombre(String nombre) {
        return List.of();
    }
}
