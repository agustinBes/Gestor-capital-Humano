package com.practical.consultora_rrhh.interfaces.services;

import java.util.List;

public interface IserviceCRUD <Id,DtoResponse,DtoRequest>{
DtoResponse guardar(DtoRequest dto);
DtoResponse actualizar(Id id,DtoRequest dto);
List<DtoResponse>  obtenerTodos();
void eliminar(Id id);
DtoResponse obtenerPorId(Id id);
}
