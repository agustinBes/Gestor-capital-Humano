package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreateItems;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;

import java.util.List;

public interface IcontrollerItemCuestionario {


cuestionarioDto completarCuestionario(Long idCuestionario, List<itemCuestionarioDtoCreateItems> items);
}
