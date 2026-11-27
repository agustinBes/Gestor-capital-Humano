package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.interfaces.auxiliares.InternalService;
import com.practical.consultora_rrhh.interfaces.genericos.IserviceCRUD;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
        * Interface que hereda de {@link IserviceCRUD} y {@link InternalService} heredando un conjunto de operaciones basicas
 * que la mayoria de los servicios requieren o exigen para
 * Garantizar la comunicacion entre servicios y con el controlador
 * Mecanismo de busqueda de factores, Revisar filtros en {@link factorFiltrosDto}
 *
         * * @author Besigniano Agustin
 *  * @since 1.0
        */
@Repository
public interface IserviceFactor extends IserviceCRUD<Long, factorDto, factorDtoCreate>, InternalService<Factor,Long> {

List<factorDto> buscar(factorFiltrosDto nombre);
boolean validarCompetenciaEnUso(Long idCompetencia);

}
