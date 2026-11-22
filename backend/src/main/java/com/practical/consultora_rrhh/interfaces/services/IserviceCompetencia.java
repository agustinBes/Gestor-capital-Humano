package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
import com.practical.consultora_rrhh.interfaces.auxiliares.InternalService;
import com.practical.consultora_rrhh.interfaces.genericos.IserviceCRUD;
import com.practical.consultora_rrhh.modelo.clases.Competencia;

import java.util.List;

public interface IserviceCompetencia extends IserviceCRUD<Long, competenciaDto, competenciaDtoCreate>, InternalService<Competencia,Long> {

     List<Competencia> validarCompetencias(List<Long> idCompetencias) ;


    List<competenciaDto> buscar(String nombre);
}
