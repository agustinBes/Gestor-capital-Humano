package com.practical.consultora_rrhh.excepciones.genericas;

import org.springframework.dao.DataIntegrityViolationException;

public class IntegridadComprometidaException extends DataIntegrityViolationException {
    public IntegridadComprometidaException(String message) {
        super(message);
    }
}
