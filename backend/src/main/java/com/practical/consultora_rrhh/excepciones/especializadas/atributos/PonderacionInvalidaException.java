package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.AtributoInvalidoException;

public class PonderacionInvalidaException extends AtributoInvalidoException {
    public PonderacionInvalidaException(String message) {
        super(message);
    }
}
