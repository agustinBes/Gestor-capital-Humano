package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;

import java.util.List;
import java.util.Map;

public interface IFactorDao extends IDaoBase<Long, Factor>, IvalidarNombreCodigoDao {
    boolean validarCompetencia(Long id, Long idCompetencia);
    List<Factor> buscar(String factor,String competencia);

    boolean existeNroOrdenPorCompetencia(short id,Long idCompetencia);

Factor actualizar(Factor factor, Map<String,String> texto, Competencia c,Short nroOrden);
}
