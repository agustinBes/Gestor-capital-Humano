package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.dto.request.preguntaDtoCreate;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CantidadOpcionesInvalidException;
import com.practical.consultora_rrhh.excepciones.genericas.FiltroVacioException;
import com.practical.consultora_rrhh.excepciones.genericas.IntegridadComprometidaException;
import com.practical.consultora_rrhh.interfaces.dao.IPreguntaDao;
import com.practical.consultora_rrhh.modelo.clases.*;
import com.practical.consultora_rrhh.modelo.enums.tipo_campo_duplicado;
import com.practical.consultora_rrhh.validaciones.comunes.DuplicadoValidators;
import com.practical.consultora_rrhh.validaciones.internas.PonderacionValidators;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * clase para validar la logica de negocio para crear y modificar una pregunta
 * garantizar el formato solicitado y verificar integridad referencial entre el Factor y la Competencia
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Service
public class PreguntaValidators  extends DuplicadoValidators {
    private final IPreguntaDao dao;
    private final PonderacionValidators ponderacionValidators;

    public PreguntaValidators(IPreguntaDao dao, PonderacionValidators ponderacionValidators) {
        this.dao = dao;
        this.ponderacionValidators = ponderacionValidators;
    }
//---------------------------------------------------------------------------------
//-----------------------------VALIDACIONES PARA CREACION---------------------------------------------------
    /**validar que el nombre elegido este disponible
     * validar que la pregunta elegida este disponible
     * validar que la sumatoria del puntaje de un total de 10
     */
    public Map<String,String> validacionInicial(preguntaDtoCreate dto,Factor f){
        Long idFactor=f.getId();
        Long idCompetencia=f.getCompetencia().getId();

        Map<String,String> normalizado=new HashMap<>();
        this.existeNombre(dto,idFactor,idCompetencia);
        normalizado.put("titulo", dto.titulo());
        this.existePregunta(dto,idFactor,idCompetencia);
        normalizado.put("pregunta", dto.pregunta());
        ponderacionValidators.ValidarPuntaje(dto.ponderaciones());
        return normalizado;
    }
//---------------------------------------------------------------------------------
//-------------------------VALIDACIONES PARA MODIFICACION--------------------------------------------------------
public Map<String,String> validarModificacion(Pregunta actual, preguntaDtoCreate dto){
Map<String,String> modificacion=new HashMap<>();

        this.validarFactor(actual,dto.idFactor());

        Long idFactor=actual.getFactor().getId();
        Long idCompetencia=actual.getFactor().getCompetencia().getId();
   String nombreNormalizado = TextoUtils.normalizar(dto.titulo());
    String preguntaNormalizada = TextoUtils.normalizar(dto.pregunta());

    if (!TextoUtils.comparar(actual.getNombre(), nombreNormalizado)) {
   this.existeNombre(dto,idFactor,idCompetencia);

modificacion.put("nombre", dto.titulo());    }

    if(!TextoUtils.comparar(actual.getPregunta(), preguntaNormalizada)) {
   this.existePregunta(dto,idFactor,idCompetencia);
        modificacion.put("pregunta", dto.pregunta());}
    if(!TextoUtils.comparar(actual.getDescripcion(), dto.descripcion())) {
        modificacion.put("descripcion", dto.descripcion());
    }
return modificacion;
}

//---------------------------------------------------------------------------------
//---------------------------------------------------------------------------------
//---------------------------------------------------------------------------------
    public void existeNombre(preguntaDtoCreate dto,Long idFactor,Long idCompetencia) {

        String normalizado = TextoUtils.normalizar(dto.titulo());
        boolean aux=dao.existeNombrePorFactorInsensible(normalizado, idFactor, idCompetencia);
        this.validarCampoDuplicado(normalizado,aux,tipo_campo_duplicado.Nombre_Pregunta);
    }
    //---------------------------------------------------------------------------------
    public void existePregunta(preguntaDtoCreate dto,Long idFactor,Long idCompetencia) {
        String pregunta = TextoUtils.normalizar(dto.pregunta());
        boolean aux = dao.existePreguntaPorFactorInsensible(pregunta, idFactor, idCompetencia);

        this.validarCampoDuplicado(pregunta,aux, tipo_campo_duplicado.PREGUNTA);
    }
//_----------------------------------------------------------------------------------------------------
    public void validarCriterio(preguntaFiltrosDto dto) {
        boolean pregunta=TextoUtils.tieneContenido(dto.pregunta());
        boolean competencia=TextoUtils.tieneContenido(dto.competencia());
        boolean factor=TextoUtils.tieneContenido(dto.factor());

        if (! pregunta && !competencia && !factor) {
            throw new FiltroVacioException("Se requiere completar al menos uno de los campos");
        }
    }
//----------------------------------------------------------------------------------------------
    /**
     * Validar que la opcion de respuesta recibida sea la misma que la de la bdd
     * En caso contrario obtener la nueva
     */
    public OpcionRespuesta validarOpcionRespuesta(Pregunta actual, preguntaDtoCreate dto,OpcionRespuesta nueva ){
        if(nueva!=null){
            if(nueva.getOpciones().size()!=dto.ponderaciones().size()){
                throw new CantidadOpcionesInvalidException("La cantidad de ponderaciones recibida no coincide con la nueva Opciond de Respuesta elegida");
            }
            return nueva;}
        if(actual.getOpcionRespuesta().getOpciones().size()!=dto.ponderaciones().size()){
            throw new CantidadOpcionesInvalidException("La cantidad de ponderaciones recibida no coincide con la nueva Opciond de Respuesta elegida");
        }
        return  null;}
//----------------------------------------------------------------------------------
    /**
     * Validar que el factor asociado a la Pregunta persistida en la bdd
     * sea el mismo que el recibido
     */
    public void validarFactor(Pregunta actual,Long idFactor){
        if(!actual.getFactor().getId().equals(idFactor)){
            throw  new IntegridadComprometidaException("Problemas de integridad no se puede modificar el factor una vez creada la pregunta");
        }}


}
