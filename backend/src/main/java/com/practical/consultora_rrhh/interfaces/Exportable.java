package com.practical.consultora_rrhh.interfaces;

import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;

import java.io.File;

/**
 * Interfaz que genera archivos en diversos formatos
 * cada servicio el conjunto de datos recibido
 * Requerida para exportar archivos en csv al evaluar candidatos CU25
 * Requerida para exportar archivos al emitir un reporte comparativo CU27
 * (queda fuera del alcance de este trabajo
 * Requerida para emitir la orden de merito en el CU28
 * @param <T> define el conjunto de datos que se requiere para generar el archivo,reporte o documento
 */
public interface Exportable<T> {

   byte [] exportar(T datos) throws ErrorExportException;
}
