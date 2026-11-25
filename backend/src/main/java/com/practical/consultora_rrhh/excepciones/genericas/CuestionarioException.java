package com.practical.consultora_rrhh.excepciones.genericas;

import org.springframework.http.HttpStatus;

public class CuestionarioException extends RuntimeException {
    private final HttpStatus status;
    public CuestionarioException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
