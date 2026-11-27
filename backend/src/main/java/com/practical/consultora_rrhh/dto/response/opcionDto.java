package com.practical.consultora_rrhh.dto.response;

import com.practical.consultora_rrhh.dto.request.opcionDtoCreate;
import com.practical.consultora_rrhh.modelo.clases.Opcion;

/**
 * Formato para devolver las opciones asociada a la opcioon de respuesta
 * @param fila
 * @param orden
 * @param descripcion
 */
public record opcionDto(
        short fila,
        short orden,
        String descripcion

) {

public opcionDto(Opcion x){
    this(
            x.getFila(),
            x.getOrden(),
            x.getDescripcion());
}
public opcionDto(opcionDtoCreate x, Long id){
    this(
            x.fila(),
            x.orden(),
            x.descripcion());
}
}
