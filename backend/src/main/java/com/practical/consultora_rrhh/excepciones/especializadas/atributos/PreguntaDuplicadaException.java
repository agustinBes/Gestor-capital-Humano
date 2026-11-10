package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.DuplicadoException;

public class PreguntaDuplicadaException extends DuplicadoException {
    public PreguntaDuplicadaException(String message) {
        super(message);
    }
}
