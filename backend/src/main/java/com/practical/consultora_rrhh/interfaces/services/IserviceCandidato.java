package com.practical.consultora_rrhh.interfaces.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.excepciones.genericas.CorruptFileException;
import com.practical.consultora_rrhh.interfaces.genericos.IserviceCRUD;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Interface que hereda de {@link IserviceCRUD} heredando un conjunto de operaciones basicas
 * que la mayoria de los servicios requieren o exigen
 * Se agregan metodos especificos para el CU 5 de importar candidatos mediante un archivo
 * Mecanismo de busqueda de candidatos, Revisar filtros en {@link candidatoFiltrosDto}
 *  * @author Besigniano Agustin
 *  * @since 1.0
 */
public interface IserviceCandidato  extends IserviceCRUD<Long,candidatoDto,candidatoFiltrosDto> {

  String importarCandidatos(Resource recurso) throws CorruptFileException;

  List<candidatoDto> buscarCandidatos(candidatoFiltrosDto filtro);


  List<Candidato> obtenerCandidatos(List<Long> candidato);
}
