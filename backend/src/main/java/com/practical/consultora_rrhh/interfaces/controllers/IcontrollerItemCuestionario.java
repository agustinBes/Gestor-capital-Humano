package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;

import java.util.List;

public interface IcontrollerItemCuestionario {


cuestionarioDto completarCuestionario(Long idCuestionario, List<itemCuestionarioDtoCreate> items);
}
