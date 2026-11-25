package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.CuestionarioException;
import org.springframework.http.HttpStatus;

public class CuestionarioActivoException extends CuestionarioException {
    public CuestionarioActivoException(String message) {

        super(message,HttpStatus.CONFLICT);
    }
}
