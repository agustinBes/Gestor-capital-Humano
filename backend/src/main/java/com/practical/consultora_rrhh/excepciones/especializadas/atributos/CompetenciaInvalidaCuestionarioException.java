package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.CuestionarioException;
import org.springframework.http.HttpStatus;

public class CompetenciaInvalidaCuestionarioException extends CuestionarioException {
    public CompetenciaInvalidaCuestionarioException(HttpStatus s,String message) {

        super(message,s);
    }
}
