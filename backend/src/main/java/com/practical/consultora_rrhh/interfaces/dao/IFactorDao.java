package com.practical.consultora_rrhh.interfaces.dao;

import com.practical.consultora_rrhh.interfaces.auxiliares.IvalidarNombreCodigoDao;
import com.practical.consultora_rrhh.modelo.clases.Factor;

public interface IFactorDao extends IDaoBase<Long, Factor>, IvalidarNombreCodigoDao {
    boolean validarCompetencia(Long id, Long idCompetencia);
}
