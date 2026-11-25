package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.dto.request.puestoCompetenciaDtoCreate;
import com.practical.consultora_rrhh.dto.request.puestoDtoCreate;
import com.practical.consultora_rrhh.dto.response.puestoDto;
import com.practical.consultora_rrhh.interfaces.auxiliares.IcompetenciaEnUso;
import com.practical.consultora_rrhh.interfaces.auxiliares.InternalService;
import com.practical.consultora_rrhh.interfaces.genericos.IserviceCRUD;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;

import java.util.List;

public interface IservicePuesto extends IserviceCRUD<Long, puestoDto,puestoDtoCreate>, InternalService<Puesto,Long>, IcompetenciaEnUso {

public List<PuestoCompetencia> guardarCaracteristicas(List<puestoCompetenciaDtoCreate> caracteristicas);

    List<puestoDto> buscar(puestoFiltrosDto dto);

    List<PuestoCompetencia> obtenerPorPuesto(Long id);
}
