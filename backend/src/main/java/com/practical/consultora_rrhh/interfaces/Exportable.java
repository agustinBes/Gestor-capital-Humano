package com.practical.consultora_rrhh.interfaces;

import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;

import java.io.File;

public interface Exportable<T> {

   byte [] exportar(T datos) throws ErrorExportException;
}
