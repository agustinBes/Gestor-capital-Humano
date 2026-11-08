package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.request.opcionRespuestaDtoCreate;
import com.practical.consultora_rrhh.dto.response.opcionRespuestaDto;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;

import java.util.List;

public interface IserviceOpcionRespuesta extends IserviceCRUD<Long, opcionRespuestaDto, opcionRespuestaDtoCreate> , InternalService<OpcionRespuesta,Long> {

    public List<opcionRespuestaDto> obtenerPorNombre(String nombre);

}
