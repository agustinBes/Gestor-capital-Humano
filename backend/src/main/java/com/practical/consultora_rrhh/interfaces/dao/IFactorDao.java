package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.interfaces.dao.consultas.IConsultaNombreCodigoDao;
import com.practical.consultora_rrhh.interfaces.dao.consultas.IConsultaCompetenciaEnUsoDao;
import com.practical.consultora_rrhh.interfaces.genericos.IDaoBase;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;

import java.util.List;
import java.util.Map;
/**
 * Interfaz especializa de la interfaz con operaciones genericas IDaoBase {@link IDaoBase}
 * tales como guardar,eliminar,listar, obtenr por cierto criterio de busqueda o campo
 * Agregamos los metodos especificados de la entidad que se requieran
 * @author  Besigniano Agustin
 * @since 1.0
 */
public interface IFactorDao extends IDaoBase<Long, Factor>, IConsultaNombreCodigoDao, IConsultaCompetenciaEnUsoDao,IreactivarEntidad<Factor> {
    boolean validarCompetencia(Long id, Long idCompetencia);
    List<Factor> buscar(String factor,String competencia);

    boolean existeNroOrdenPorCompetencia(short id,Long idCompetencia);
    boolean existeCompetenciaEnUso(Long idCompetencia);

Factor actualizar(Factor factor, Map<String,String> texto, Competencia c,Short nroOrden);
}
