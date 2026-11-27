package com.practical.consultora_rrhh.interfaces.controllers;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.dto.response.reporte.evaluacionesOrdenMeritoDto;
import com.practical.consultora_rrhh.dto.response.reporte.puestoOrdenMeritoDto;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Peticiones requeridas durante el flujo del CU 28
 */
public interface IcontrollerOrdenMerito {


    public ResponseEntity<List<puestoOrdenMeritoDto>> buscar(puestoFiltrosDto dto) ;
    public ResponseEntity<List<evaluacionesOrdenMeritoDto>> emitirOrdenMerito(String id) ;

    // Paso 7: generar y devolver el reporte
    public ResponseEntity<byte[]> generarReporte(@RequestParam("cuestionarios")List<@Valid Long> cuestionarios,String idPuesto) throws ErrorExportException;
}
