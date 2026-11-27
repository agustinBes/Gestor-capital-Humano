package com.practical.consultora_rrhh.interfaces.genericos;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Define metodos basicos de peticiones a las que deben dar respuesta los controladores
 *
 * @param <DtoRequest> usado para gestionar creacion y modificacion de entidades
 * @param <DtoResponse> usado para transmitir informacion al usuario
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */

public interface IControllerCRUD<DtoResponse,DtoRequest> {

 ResponseEntity<DtoResponse> guardar(DtoRequest dto);

 ResponseEntity<DtoResponse> actualizar(String id,DtoRequest dto);

 ResponseEntity<String> eliminar(String id,String idConsultor);

 ResponseEntity<List<DtoResponse>> obtenerTodos();

 ResponseEntity<DtoResponse> obtenerPorId(String id);
}