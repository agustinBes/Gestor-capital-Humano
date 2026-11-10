package com.practical.consultora_rrhh.interfaces.auxiliares;

import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;

public interface InternalMediator <EntidadA,EntidadB> {

  default   boolean EntidadAEnUso(Long id){return false;};

    default   boolean EntidadBEnUso(Long id){return false;};

    EntidadA obtenerEntidadA(Long id);

     EntidadB obtenerEntidadB(Long id);

     boolean validarEntidadA(Long id);

     boolean validarEntidadB(Long id);
}