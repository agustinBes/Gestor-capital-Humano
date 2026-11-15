package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.request.puestoDtoCreate;
import com.practical.consultora_rrhh.dto.response.puestoDto;
import com.practical.consultora_rrhh.modelo.clases.Puesto;

public interface IservicePuesto extends IserviceCRUD<Long, puestoDto,puestoDtoCreate>, InternalService<Puesto,Long>,IcompetenciaEnUso{
}
