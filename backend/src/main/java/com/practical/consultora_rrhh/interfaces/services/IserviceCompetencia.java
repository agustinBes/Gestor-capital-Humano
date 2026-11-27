package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
import com.practical.consultora_rrhh.interfaces.auxiliares.InternalService;
import com.practical.consultora_rrhh.interfaces.genericos.IserviceCRUD;
import com.practical.consultora_rrhh.modelo.clases.Competencia;

import java.util.List;
/**
 * Interface que hereda de {@link IserviceCRUD} y {@link InternalService} heredando un conjunto de operaciones basicas
 * que la mayoria de los servicios requieren o exigen para
 * Garantizar la comunicacion entre servicios y con el controlador
 *
 * * @author Besigniano Agustin
 *  * @since 1.0
 */
public interface IserviceCompetencia extends IserviceCRUD<Long, competenciaDto, competenciaDtoCreate>, InternalService<Competencia,Long> {

     List<Competencia> validarCompetencias(List<Long> idCompetencias) ;


    List<competenciaDto> buscar(String nombre);


}
