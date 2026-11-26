package com.practical.consultora_rrhh.excepciones.especializadas.atributos;

import com.practical.consultora_rrhh.excepciones.genericas.CuestionarioException;
import org.springframework.http.HttpStatus;

public class BloqueCompletadoException extends CuestionarioException {

public BloqueCompletadoException(String s, HttpStatus httpStatus){

    super(s,httpStatus);
}
}
