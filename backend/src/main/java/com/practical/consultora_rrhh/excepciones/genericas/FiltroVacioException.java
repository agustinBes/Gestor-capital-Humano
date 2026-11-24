package com.practical.consultora_rrhh.excepciones.genericas;

public class FiltroVacioException extends RuntimeException {
    public FiltroVacioException() {
        super("Se requiere completar al menos uno de los filtros");    }
}
