package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.AtributoInvalidoException;

public class OrdenNoConsecutivoException extends AtributoInvalidoException {
    public OrdenNoConsecutivoException(String s) {
        super(s);
    }
}
