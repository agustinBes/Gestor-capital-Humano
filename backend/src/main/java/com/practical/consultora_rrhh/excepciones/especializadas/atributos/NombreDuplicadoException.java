package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.DuplicadoException;

public class NombreDuplicadoException extends DuplicadoException {
    public NombreDuplicadoException(String message) {
        super("El nombre ya esta en uso"+ message);    }
}
