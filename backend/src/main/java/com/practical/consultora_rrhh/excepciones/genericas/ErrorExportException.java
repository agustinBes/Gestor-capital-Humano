package com.practical.consultora_rrhh.excepciones.genericas;

import java.io.IOException;

public class ErrorExportException extends IOException {
    public ErrorExportException(String message) {
        super(message);
    }
}
