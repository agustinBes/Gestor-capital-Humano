package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
import com.practical.consultora_rrhh.interfaces.dao.ICompetenciaDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServiceCompetencia implements IserviceCompetencia {

    private final ICompetenciaDao dao;
    public ServiceCompetencia(ICompetenciaDao competenciaDao) {
        this.dao = competenciaDao;
    }
    public Competencia obtenerEntidad(Long aLong) {
        return null;
    }

    public void validarEntidad(Long aLong) {

    }

    public competenciaDto guardar(competenciaDtoCreate dto) {
        return null;
    }

    public competenciaDto actualizar(Long aLong, competenciaDtoCreate dto) {
        return null;
    }

    public List<competenciaDto> obtenerTodos() {

        return dao.obtenerTodos()
                .stream()
                .map(o->new competenciaDto(o,true))
                .toList();
    }

    @Override
    public List<competenciaDto> buscar(String nombre) {
       if(nombre==null || nombre.isEmpty()){
           return  this.obtenerTodos();}
           String normalizar= TextoUtils.normalizar(nombre);
return  dao.obtenerPorNombreInsensible(normalizar)
        .stream()
        .map(o -> new competenciaDto(o,false))
        .toList();
       }


    public void eliminar(Long aLong) {
    }

    public competenciaDto obtenerPorId(Long id) {
        return null;
    }
}
