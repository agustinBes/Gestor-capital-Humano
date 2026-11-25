package com.practical.consultora_rrhh.excepciones.genericas;

import org.springframework.http.HttpStatus;

import java.io.IOException;

public class FileException extends IOException {
  private final HttpStatus status;
  public FileException(String s, HttpStatus status) {
    this.status = status;
    super(s);}
  public HttpStatus getStatus() {
    return status;
  }
  }
