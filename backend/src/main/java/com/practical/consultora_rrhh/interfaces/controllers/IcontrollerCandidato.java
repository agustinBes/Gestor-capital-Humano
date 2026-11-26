package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.request.candidatoDtoAuth;
import com.practical.consultora_rrhh.dto.request.candidatoDtoCreate;
import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;
import com.practical.consultora_rrhh.interfaces.genericos.IControllerCRUD;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface IcontrollerCandidato extends IControllerCRUD<candidatoDto, candidatoDtoCreate> {


    @GetMapping("/login/auth")
    ResponseEntity<cuestionarioDto> autenticarCandidato(@Valid candidatoDtoAuth candidatoDto);
}
