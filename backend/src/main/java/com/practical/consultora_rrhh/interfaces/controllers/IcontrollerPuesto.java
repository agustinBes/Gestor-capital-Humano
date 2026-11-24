package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.dto.request.puestoDtoCreate;
import com.practical.consultora_rrhh.dto.response.puestoDto;
import com.practical.consultora_rrhh.interfaces.genericos.IControllerCRUD;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IcontrollerPuesto extends IControllerCRUD<puestoDto, puestoDtoCreate> {

    ResponseEntity<List<puestoDto>> buscar(puestoFiltrosDto dto);
}
