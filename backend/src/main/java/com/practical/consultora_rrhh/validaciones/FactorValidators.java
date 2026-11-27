package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.excepciones.genericas.FiltroVacioException;
import com.practical.consultora_rrhh.excepciones.genericas.IntegridadComprometidaException;
import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import com.practical.consultora_rrhh.interfaces.auxiliares.IvalidarExistenciaNombreCodigoService;
import com.practical.consultora_rrhh.modelo.clases.Factor;
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
public class FactorValidators     extends DuplicadoValidators implements IvalidarExistenciaNombreCodigoService {
    private final IFactorDao dao;

    public FactorValidators(IFactorDao dao) {
        this.dao = dao;
    }

    @Override
    public void existePorNombreInsensible(String nombre) {

        String nombreNormalizado = TextoUtils.normalizar(nombre);
        boolean aux = dao.existePorNombreInsensible(nombre);
        this.validarCampoDuplicado(nombreNormalizado, aux, tipo_campo_duplicado.NOMBRE);
    }

    @Override
    public void existeCodigoInsensible(String codigo) {
        String codigoNormalizado = TextoUtils.normalizar(codigo);
        boolean aux = dao.existePorCodigoInsensible(codigo);
        this.validarCampoDuplicado(codigoNormalizado, aux, tipo_campo_duplicado.CODIGO);
    }

    public void existeOrdenPorCompetencia(short nroOrden, Long idCompetencia) {
        boolean aux = dao.existeNroOrdenPorCompetencia(nroOrden, idCompetencia);
        this.validarCampoDuplicado(String.valueOf(nroOrden), aux, tipo_campo_duplicado.NRO_ORDEN);
    }

    public Map<String, String> validarModificaciones(Factor f, factorDtoCreate dto) {
        Map<String, String> map = new HashMap<>();
        boolean nombre = this.CambioTexto(f.getNombre(), dto.nombre());
        boolean descripcion=this.CambioTexto(f.getDescripcion(), dto.descripcion());
        String codigo=TextoUtils.normalizar(dto.codigo());
        if(!f.getCodigo().equals(codigo)){
            throw new IntegridadComprometidaException("Problemas de integridad no se puede modificar el codigo una vez creado el factor");
        }
        if(nombre){

            this.existePorNombreInsensible(dto.nombre());
            map.put("nombre",TextoUtils.normalizar(dto.nombre()));}

        if(descripcion){
            map.put("descripcion",TextoUtils.normalizar(dto.descripcion()));
        }
        if(map.isEmpty()){
            return null;
        }

        return map;}


    public void validarCriterios(factorFiltrosDto dto) {

        if (!TextoUtils.tieneContenido(dto.competencia()) &&
                !TextoUtils.tieneContenido(dto.factor())) {
            throw new FiltroVacioException();
        }
    }


    public boolean CambioTexto(String actual, String cambio) {
        return (!TextoUtils.comparar(actual, cambio));
    }
}