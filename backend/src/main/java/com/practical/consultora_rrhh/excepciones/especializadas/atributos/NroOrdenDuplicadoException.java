package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.DuplicadoException;

public class NroOrdenDuplicadoException extends DuplicadoException {
    public NroOrdenDuplicadoException(String message) {
        super(message);
    }
}
