package com.practical.consultora_rrhh.interfaces.auxiliares;

import com.practical.consultora_rrhh.modelo.clases.Consultor;

/**
 * Recuperar consultores para el proceso de auditoria
 */
public interface Iconsultor {

    Consultor obtenerConsultor(Long id);
   default Consultor obtenerConsultor(String usuario){return  null;};
}
