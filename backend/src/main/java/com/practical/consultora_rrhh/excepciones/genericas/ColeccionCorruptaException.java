package com.practical.consultora_rrhh.excepciones.genericas;

import org.springframework.http.HttpStatus;

public class ColeccionCorruptaException extends CuestionarioException {
    public ColeccionCorruptaException(String message) {


        super(message, HttpStatus.CONFLICT);
    }
}
