package com.practical.consultora_rrhh.excepciones.genericas;

import org.springframework.http.HttpStatus;

import java.io.IOException;

public class CorruptFileException extends FileException    {

public CorruptFileException(HttpStatus status, String mensagem) {
    super(mensagem,status);
    }
}
