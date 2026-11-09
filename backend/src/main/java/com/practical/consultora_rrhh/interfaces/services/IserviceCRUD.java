package com.practical.consultora_rrhh.interfaces.services;

import java.util.List;

/**
 * Define metodos basicos que todas las entidades deben garantizar e implementar en especial aquellas que
 * se comunican con controladores
 *
 * @param <DtoRequest> usado para gestionar creacion y modificacion de entidades
 * @param <DtoResponse> usado para transmitir informacion al usuario
 * @param <Id>  definimos el tipo de clave primaria usada por la entidad
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */

public interface IserviceCRUD <Id,DtoResponse,DtoRequest>{
DtoResponse guardar(DtoRequest dto);
DtoResponse actualizar(Id id,DtoRequest dto);
List<DtoResponse>  obtenerTodos();
void eliminar(Id id);
DtoResponse obtenerPorId(Id id);
}
