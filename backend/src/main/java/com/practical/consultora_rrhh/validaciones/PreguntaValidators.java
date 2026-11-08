package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.preguntaDtoCreate;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CantidadOpcionesInvalidException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.SumaPonderacionesInvalidException;
import com.practical.consultora_rrhh.excepciones.genericas.IntegridadComprometidaException;
import com.practical.consultora_rrhh.interfaces.dao.IPreguntaDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.interfaces.services.IserviceOpcionRespuesta;
import com.practical.consultora_rrhh.modelo.clases.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * clase para validar la logica de negocio para crear y modificar una pregunta
 * garantizar el formato solicitado y verificar integridad referencial entre el Factor y la Competencia
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Service
public class PreguntaValidators   {
    private final IPreguntaDao dao;
    private final IserviceFactor serviceFactor;
    private final IserviceOpcionRespuesta serviceOpcionRespuesta;

    public PreguntaValidators(IPreguntaDao dao, IserviceFactor serviceFactor, IserviceOpcionRespuesta serviceOpcionRespuesta) {

        this.dao = dao;
    this.serviceFactor = serviceFactor;
        this.serviceOpcionRespuesta = serviceOpcionRespuesta;
    }
//---------------------------------------------------------------------------------
//---------------------------------------------------------------------------------

    /**
     * validar que el nombre elegido este disponible
     * validar que la pregunta elegida este disponible
     * validar que la sumatoria del puntaje de un total de 10
     */

    public Map<String,String> validacionInicial(preguntaDtoCreate dto){
        Map<String,String> normalizado=new HashMap<>();
        this.existeNombre(dto);
        normalizado.put("titulo", dto.titulo());
        this.existePregunta(dto);
        normalizado.put("pregunta", dto.pregunta());
        this.ValidarPuntaje(dto.ponderaciones());
        return normalizado;
    }
//---------------------------------------------------------------------------------
//---------------------------------------------------------------------------------
public Map<String,String> validarModificacion(Pregunta actual, preguntaDtoCreate dto){
Map<String,String> modificacion=new HashMap<>();
        this.validarFactor(actual,dto.factor().id());
        this.validarCompetencia(actual,dto.factor().competencia().id());
        OpcionRespuesta aux=this.validarOpcionRespuesta(actual,dto);

   String nombreNormalizado = TextoUtils.normalizar(dto.titulo());
    String preguntaNormalizada = TextoUtils.normalizar(dto.pregunta());

    if (!TextoUtils.comparar(actual.getNombre(), nombreNormalizado)) {

    }
    return  false;

}



//---------------------------------------------------------------------------------
//---------------------------------------------------------------------------------

    /**
     * Validar que el factor asociado a la entidad persistida en la bdd
     * sea el mismo que el recibido
     */
    public void validarFactor(Pregunta actual,Long idFactor){
            if(!actual.getFactor().getId().equals(idFactor)){
throw  new IntegridadComprometidaException("Problemas de integridad no se puede modificar el factor una vez creada la pregunta");
       }}
 //----------------------------------------------------------------------------------
    /**
     * Validar que el id recibido sea el mismo asociado a la entidad persistida en la bdd
      */
    public void validarCompetencia(Factor f, Long idCompetencia) {
        if (f.getCompetencia() == null) {
            throw new IntegridadComprometidaException(
                    "El factor no tiene una competencia asociada");}
        if (!f.getCompetencia().getId().equals(idCompetencia)) {
            throw new IntegridadComprometidaException(
                    "La competencia recibida no tiene relación con el factor recibido");
        }
    }
//-----------------------------------------------------------

    /**
     * Validar que la opcion de respuesta recibida sea la misma que la de la bdd
     * En caso contrario obtener la nueva
     */
    public OpcionRespuesta validarOpcionRespuesta(Pregunta actual, preguntaDtoCreate dto ){
        OpcionRespuesta nueva;
        if(!actual.getOpcionRespuesta().getId().equals(dto.id_opcionRespuesta())){
            nueva=serviceOpcionRespuesta.obtenerEntidad(dto.id_opcionRespuesta());

            if(nueva.getOpciones().size()!=dto.ponderaciones().size()){
                throw new CantidadOpcionesInvalidException("La cantidad de ponderaciones recibida no coincide con la nueva Opciond de Respuesta elegida");
            }
            this.ValidarPuntaje(dto.ponderaciones());

                   return nueva;}
        if(actual.getOpcionRespuesta().getOpciones().size()!=dto.ponderaciones().size()){
            throw new CantidadOpcionesInvalidException("La cantidad de ponderaciones recibida no coincide con la nueva Opciond de Respuesta elegida");

        }
        this.ValidarPuntaje(dto.ponderaciones());

        return  null;}

    //---------------------------------------------------------------------------------
//---------------------------------------------------------------------------------
    public void existeNombre(preguntaDtoCreate dto) {
        String normalizado=TextoUtils.normalizar(dto.titulo());
        dao.existeNombrePorFactorInsensible(
                    normalizado,
                dto.factor().id(),
                        dto.factor().competencia().id());}
//---------------------------------------------------------------------------------
    public void existePregunta(preguntaDtoCreate dto) {
        String pregunta=TextoUtils.normalizar(dto.pregunta());

        dao.existePreguntaPorFactorInsensible(
                pregunta,
                dto.factor().id(),
                dto.factor().competencia().id());
    }
//---------------------------------------------------------------------------------
    public void ValidarPuntaje(List<Short> ponderacion) {
int sum=0;
      for(Short puntaje : ponderacion) {
        sum=sum+puntaje;}
      if(sum!=10){
            throw new SumaPonderacionesInvalidException("La suma de las ponderaciones en total debe ser 10");}
    }
//---------------------------------------------------------------------------------

    /**Funcion auxiliar  para simplificar la logica del servicio
     * Encargada de construir las ponderaciones entre la opcion y el puntaje a persistir
     */
    public List<Ponderacion> construirPonderaciones(List<Short> ponderacion,List<Opcion> opciones,Long idopcionRespuesta) {
        List<Ponderacion> ponderaciones=new ArrayList<>();

        for (int i = 0; i < opciones.size(); i++) {
            ponderaciones.add(
                     Ponderacion.builder()
                             .fila(opciones.get(i).getFila())
                             .puntaje(ponderacion.get(i))
                             .opcionRespuesta(idopcionRespuesta)
                             .build());}
        return ponderaciones;}
}
