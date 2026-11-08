package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.AtributoInvalidoException;

public class CantidadOpcionesInvalidException extends AtributoInvalidoException {
    public CantidadOpcionesInvalidException(String message) {
        super(message);
    }
}
