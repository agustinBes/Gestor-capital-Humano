package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dao.OpcionRespuestaDaoImpl;
import com.practical.consultora_rrhh.dto.request.opcionDtoCreate;
import com.practical.consultora_rrhh.dto.request.opcionRespuestaDtoCreate;
import com.practical.consultora_rrhh.dto.response.opcionDto;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CantidadOpcionesInvalidException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.DescripcionDuplicadaException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.OrdenNoConsecutivoException;
import com.practical.consultora_rrhh.modelo.clases.Opcion;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * Componente encargado de centralizar las validaciones relacionadas
 * con la gestión de opciones de respuesta.
 *
 * Contiene las reglas de validación correspondientes a la entidad
 * OpcionRespuesta y delega las validaciones específicas de cada
 * opción a {@link OpcionValidators}.
 *
 * Ante el incumplimiento de una regla de negocio, lanza excepciones
 * personalizadas que permiten identificar el tipo de error producido.
 *
* @author  Besigniano Agustin
 @since 1.0
 */
@Service
public class OpcionRespuestaValidators  {
    private final OpcionRespuestaDaoImpl dao;
    private final OpcionValidators opcionValidators;

    public OpcionRespuestaValidators(OpcionRespuestaDaoImpl dao, OpcionValidators opcionValidators) {
      super();
        this.dao= dao;
        this.opcionValidators = opcionValidators;
    }
//----------------------------------------------------------


    public void existeNombre(String nombre) {
       dao.existePorNombreInsensible(nombre);
    }

  // comprobar la disponibilidad del nombre y texto para modificacion
public boolean validarAlteracionTexto(String actual, String nuevo) {
        boolean coinciden= TextoUtils.comparar(actual,nuevo);
        if(coinciden){return false;}
existeNombre(nuevo);
            return true;}



//-------------------------------------------------------------------------
    // validar Opciones asociadas a la OpcionRespuesta para la creacion

    public void validarFormatoOpciones(List<opcionDtoCreate> opcion) {

        if (!opcionValidators.ordenConsecutivo(opcion)) {
            throw new OrdenNoConsecutivoException(
                    "Las opciones deben tener un orden consecutivo comenzando desde 1");
        }

        if (!opcionValidators.descripcionUnica(opcion)) {
            throw
new DescripcionDuplicadaException("La descripciones recibida ya existe en el sistema");
        }
    }
    /*
    * */
public void validarAlteracionCantidadOpciones(Integer actual, List<opcionDtoCreate> modificacion) {
        if(actual!=opcionValidators.cantidadOpciones(modificacion)){
            throw
        new CantidadOpcionesInvalidException("No esta permitido agregar ni eliminar opciones de las que ya existian");}

        }

    /**
     * Detectar alteraciones de descripcion y orden de visualizacion de las opciones
     */

    public List<opcionDto> ValidarAlteracionOpcion(Long id,List<Opcion> actual, List<opcionDtoCreate> dto) {
        List<opcionDtoCreate> aux = new ArrayList<>();
        Integer modificacion = 0;

        for (int i = 0; i < actual.size(); i++) {
            Opcion x = actual.get(i);
            opcionDtoCreate opcion = dto.get(i);
            if (x.getOrden() != opcion.orden()
                    || !TextoUtils.comparar(x.getDescripcion(), opcion.descripcion())) {
                aux.add(opcion);
            modificacion++;}
            else{
                aux.add(new opcionDtoCreate(actual.get(i).getFila(),x.getOrden(),x.getDescripcion()));
            }}
        if(modificacion==0){
            return new ArrayList<>();}

this.validarFormatoOpciones(aux);
                   return aux.stream()
                    .map(o -> new opcionDto(
                            o,id))
                    .toList();
        }

    /**
     *        Detectar modificaciones de la descripcion
     *        Detectar si la cantidad de {@link Opcion} es la misma que la persistida en la bdd durante la creacion
     *        Detectar si el nuevo formato de las opciones  es adecuado
     */
    public boolean validarFormatoModificaciones(Long id, OpcionRespuesta actual, opcionRespuestaDtoCreate dto) {
      boolean descripcion;
        descripcion = this.validarAlteracionTexto(actual.getDescripcion(), dto.descripcion());
        this.validarAlteracionCantidadOpciones(actual.getOpciones().size(), dto.opciones());
        this.validarFormatoOpciones(dto.opciones());

        return descripcion;  }
}

    //-----------------------------------------------------------





