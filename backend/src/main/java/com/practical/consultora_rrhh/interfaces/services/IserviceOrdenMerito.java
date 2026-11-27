package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.dto.response.reporte.evaluacionesOrdenMeritoDto;
import com.practical.consultora_rrhh.dto.response.puestoOrdenMeritoDto;
import com.practical.consultora_rrhh.interfaces.Exportable;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;

import java.util.List;

public interface IserviceOrdenMerito extends Exportable<List<Long>> {

    List<puestoOrdenMeritoDto> buscar(puestoFiltrosDto dto);

    List<evaluacionesOrdenMeritoDto> emitirOrdenMerito(Long id);

    byte[] generarReporte(List<Cuestionario> completo,List<Cuestionario> incompleto,List<Cuestionario> sinContestar);
}
