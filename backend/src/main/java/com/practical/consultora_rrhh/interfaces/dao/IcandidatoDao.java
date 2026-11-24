package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.Candidato;

import java.util.List;

public interface IcandidatoDao extends IDaoBase<Long, Candidato> {

List<Candidato> buscar(candidatoFiltrosDto dto);
}
