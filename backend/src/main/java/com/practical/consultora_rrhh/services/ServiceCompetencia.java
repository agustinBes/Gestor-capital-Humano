package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.modelo.clases.Competencia;

import java.util.List;

public class ServiceCompetencia implements IserviceCompetencia {

    @Override
    public Competencia obtenerEntidad(Long aLong) {
        return null;
    }

    @Override
    public void validarEntidad(Long aLong) {

    }

    @Override
    public competenciaDto guardar(competenciaDtoCreate dto) {
        return null;
    }

    @Override
    public competenciaDto actualizar(Long aLong, competenciaDtoCreate dto) {
        return null;
    }

    @Override
    public List<competenciaDto> obtenerTodos() {
        return List.of();
    }

    @Override
    public void eliminar(Long aLong) {

    }

    @Override
    public competenciaDto obtenerPorId(Long aLong) {
        return null;
    }
}
