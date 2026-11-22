package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.puestoDtoCreate;
import com.practical.consultora_rrhh.interfaces.dao.IPuestoDao;
import com.practical.consultora_rrhh.interfaces.auxiliares.IvalidarExistenciaNombreCodigoService;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import com.practical.consultora_rrhh.modelo.enums.tipo_campo_duplicado;
import com.practical.consultora_rrhh.validaciones.comunes.DuplicadoValidators;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Clase que valida la logica de servicio para creacion y modificacion de la clase {@link Puesto}
 * y la clase asociacion {@link PuestoCompetencia}
 * que surge de la relacion de cardinalidad entre Puesto y la clase {@link Competencia}
 *
 * @author  Besigniano Agustin
 * @since  1.0
 */
@Service

public class PuestoValidators extends DuplicadoValidators implements IvalidarExistenciaNombreCodigoService {

    private final IPuestoDao dao;

    public PuestoValidators(IPuestoDao dao) {
        this.dao = dao;
    }

    /**
     * Validamos disponibilidad de nombre y codigo normalizados
     * Normalizamos la descripcion y la empresa
     * Retornamos un mapa para guardar en la nueva entidad o actualizar la existente con atributos modificados
     * @see TextoUtils
     */
    public Map<String, String> validarCreacionModificacion(Puesto puesto, puestoDtoCreate dto) {

        Map<String, String> respuesta = new HashMap<>();

        String nombre = TextoUtils.normalizar(dto.nombre());
        String codigo = TextoUtils.normalizar(dto.codigo());
        String descripcion = TextoUtils.normalizar(dto.descripcion());
        String empresa = TextoUtils.normalizar(dto.empresa());

        if (puesto == null || !TextoUtils.comparar(puesto.getNombre(), nombre)) {
            existePorNombreInsensible(nombre);
            respuesta.put("nombre", nombre);}

        if (puesto == null ) {
            existeCodigoInsensible(codigo);
            respuesta.put("codigo", codigo);}
        if (puesto == null || !TextoUtils.comparar(puesto.getDescripcion(), descripcion)) {
            respuesta.put("descripcion", descripcion);}

        if (puesto == null || !TextoUtils.comparar(puesto.getEmpresa(), empresa)) {
            respuesta.put("empresa", empresa);}

        return respuesta;}


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


