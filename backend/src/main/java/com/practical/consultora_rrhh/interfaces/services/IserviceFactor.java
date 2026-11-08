package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IserviceFactor extends IserviceCRUD<Long, factorDto, factorDtoCreate>, InternalService<Factor,Long>,IvalidarExistenciaNombreCodigoService<factorDto> {

void validarConsistencia(Long id,Long idCompetencia);
List<factorDto> obtenerPorNombre(String nombre);
}
