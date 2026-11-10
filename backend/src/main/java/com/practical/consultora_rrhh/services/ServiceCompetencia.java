package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
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

    public competenciaDto guardar(competenciaDtoCreate dto) {


        return null;
    }

    public competenciaDto actualizar(Long aLong, competenciaDtoCreate dto) {
        return null;
    }

    public List<competenciaDto> obtenerTodos() {

        return dao.obtenerTodos()
                .stream()
                .map(competenciaDto::new)
                .toList();
    }

    @Override
    public List<competenciaDto> buscar(String nombre) {
       if(nombre==null || nombre.isEmpty()){
           return  this.obtenerTodos();}
           String normalizar= TextoUtils.normalizar(nombre);
return  dao.obtenerPorNombreInsensible(normalizar)
        .stream()
        .map(competenciaDto::new)
        .toList();
       }


    public void eliminar(Long aLong) {
    }

    public competenciaDto obtenerPorId(Long id) {
        return null;
    }

//-------------------------------------------------------------------
    public Competencia obtenerEntidad(Long id){
     return    dao.obtenerPorId(id).orElseThrow(() ->
            new EntityNotFoundException("No se encontró la entidad con el id: "+id+" en el sistema"));

}

    public void validarEntidad(Long aLong) {

    }

}
