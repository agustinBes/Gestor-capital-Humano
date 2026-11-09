package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.excepciones.genericas.FiltroVacioException;
import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import org.springframework.stereotype.Service;

@Service
public class FactorValidators   {
private final IFactorDao dao;
public FactorValidators(IFactorDao dao) {
    this.dao=dao;
}
    public void existeNombre(String nombre) {
        dao.existePorNombreInsensible(nombre);
    }
    public void existeCodigo(String nombre) {
        dao.existePorNombreInsensible(nombre);
    }

public void validarCriterios(factorFiltrosDto dto) {

        if (!TextoUtils.tieneContenido(dto.competencia()) &&
                !TextoUtils.tieneContenido(dto.factor())) {
            throw new FiltroVacioException("Se requiere completar al menos uno de los campos");
        }}
}


