package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.DuplicadoException;

public class DescripcionDuplicadaException extends DuplicadoException {
    public DescripcionDuplicadaException(String message) {
        super(message);
    }
}
