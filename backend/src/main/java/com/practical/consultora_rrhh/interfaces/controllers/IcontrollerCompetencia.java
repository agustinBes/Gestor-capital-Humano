package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IcontrollerCompetencia extends  IControllerCRUD<competenciaDto, competenciaDtoCreate>{
ResponseEntity<List<competenciaDto>> buscar(String nombre);

}
