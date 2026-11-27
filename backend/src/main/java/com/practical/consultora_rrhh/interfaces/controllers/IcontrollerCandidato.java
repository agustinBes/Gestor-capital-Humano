package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.request.candidatoDtoAuth;
import com.practical.consultora_rrhh.dto.request.candidatoDtoCreate;
import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;
import com.practical.consultora_rrhh.interfaces.genericos.IControllerCRUD;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Interfaz que hereda de las operaciones genericas de insercion,actualizacion
 * eliminacion y lista que ofrece la interfaz {@link IControllerCRUD}
 a su vez agrega los metodos especificos que esta obligado a implementar el
 controlador especifico y los tipos de retornos con los que opera
 @author Besigniano Agustin
 @since 1.0
 */
public interface IcontrollerCandidato extends IControllerCRUD<candidatoDto, candidatoDtoCreate> {

    
    @PostMapping ("/candidato/auth")
    ResponseEntity<cuestionarioDto> autenticarCandidato(@Valid @RequestBody candidatoDtoAuth candidatoDto);
}
