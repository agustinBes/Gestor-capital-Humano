package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.CuestionarioException;
import org.springframework.http.HttpStatus;

public class ClaveInvalidaException extends CuestionarioException {

    public ClaveInvalidaException(HttpStatus status, String mensaje) {

        super(mensaje,status);
    }
}
