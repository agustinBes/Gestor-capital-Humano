package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.excepciones.genericas.IntegridadComprometidaException;
import com.practical.consultora_rrhh.interfaces.dao.ICompetenciaDao;
import com.practical.consultora_rrhh.interfaces.auxiliares.IvalidarExistenciaNombreCodigoService;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.enums.tipo_campo_duplicado;
import com.practical.consultora_rrhh.validaciones.comunes.DuplicadoValidators;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que Valida logica de negocio del sistema
 * e interactua con la capa de persistencia
 * permitiendo dejar los servicios lo mas limpios posibles de codigo
 * @author Besigniano Agustin
 * @since 1.0
 */

@Service
public class CompetenciaValidators  extends DuplicadoValidators implements IvalidarExistenciaNombreCodigoService {
    private final ICompetenciaDao dao;

    public CompetenciaValidators(ICompetenciaDao dao) {
        this.dao = dao;
    }


public Map<String,String> validarCreacion(competenciaDtoCreate dto){
        Map<String,String> respuesta = new HashMap<>();
        existePorNombreInsensible(dto.nombre());
        respuesta.put("nombre",TextoUtils.normalizar(dto.nombre()));
        existeCodigoInsensible(dto.codigo());
        respuesta.put("codigo",TextoUtils.normalizar(dto.codigo()));
        respuesta.put(("descripcion"),TextoUtils.normalizar(dto.descripcion()));
        return respuesta;
}
    public Map<String,String> validarModificacion(competenciaDtoCreate dto, Competencia actual){
        Map<String,String> respuesta = new HashMap<>();

        if(!actual.getCodigo().equals(dto.codigo())){
            throw new IntegridadComprometidaException("No se puede alterar el codigo una vez creado");
        }

        if(!actual.getNombre().equals(dto.nombre())) {
            existePorNombreInsensible(dto.nombre());
            respuesta.put("nombre", TextoUtils.normalizar(dto.nombre()));
        }
        if(!actual.getDescripcion().equals(dto.descripcion())) {
            respuesta.put("descripcion", TextoUtils.normalizar(dto.descripcion()));
        }
        return respuesta;
        }


    @Override
    public void existePorNombreInsensible(String nombre) {
        String nombreNormalizado= TextoUtils.normalizar(nombre);
        boolean aux = dao.existePorNombreInsensible(nombreNormalizado);
        this.validarCampoDuplicado(nombreNormalizado, aux, tipo_campo_duplicado.NOMBRE);
}
        @Override
    public void existeCodigoInsensible(String codigo) {
        String codigoNormalizado= TextoUtils.normalizar(codigo);
            boolean aux = dao.existePorCodigoInsensible(codigoNormalizado);
            this.validarCampoDuplicado(codigoNormalizado, aux, tipo_campo_duplicado.CODIGO);
        }
}
