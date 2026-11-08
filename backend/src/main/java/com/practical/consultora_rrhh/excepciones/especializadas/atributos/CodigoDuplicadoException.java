package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.DuplicadoException;

public class CodigoDuplicadoException extends DuplicadoException {
    public CodigoDuplicadoException(String message) {
        super(message);
    }
}
