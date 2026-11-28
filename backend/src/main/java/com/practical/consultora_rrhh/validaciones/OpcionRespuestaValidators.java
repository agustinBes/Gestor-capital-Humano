package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.opcionDtoCreate;
import com.practical.consultora_rrhh.dto.request.opcionRespuestaDtoCreate;
import com.practical.consultora_rrhh.dto.response.opcionDto;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CantidadOpcionesInvalidException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.DescripcionDuplicadaException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.OrdenNoConsecutivoException;
import com.practical.consultora_rrhh.modelo.clases.Opcion;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.validaciones.comunes.DuplicadoValidators;
import com.practical.consultora_rrhh.validaciones.internas.OpcionValidators;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Componente encargado de centralizar las validaciones relacionadas
 * con la gestión de opciones de respuesta.
 * Contiene las reglas de validación correspondientes a la entidad
 * OpcionRespuesta y delega las validaciones específicas de cada
 * opción a {@link OpcionValidators}.
 * Ante el incumplimiento de una regla de negocio, lanza excepciones
 * personalizadas que permiten identificar el tipoDocumento de error producido.
 *
* @author  Besigniano Agustin
 @since 1.0
 */
@Service
public class OpcionRespuestaValidators  extends DuplicadoValidators {
    public  OpcionValidators opcionValidators=new OpcionValidators();
//----------------------------------------------------------
  // comprobar la disponibilidad del  texto para modificacion
public boolean validarAlteracionTexto(String actual, String nuevo) {
    boolean coinciden = TextoUtils.comparar(actual, nuevo);
    return !coinciden;}
//-------------------------------------------------------------------------
  // validar Opciones asociadas a la OpcionRespuesta para la creacion

    public void validarFormatoOpciones(List<opcionDtoCreate> opcion) {
opcionValidators.filaUnica(opcion);

        if (!opcionValidators.ordenConsecutivo(opcion)) {
            throw new OrdenNoConsecutivoException(
                    "Las opciones deben tener un orden consecutivo comenzando desde 1");}
        if (!opcionValidators.descripcionUnica(opcion)) {
            throw   new DescripcionDuplicadaException("La descripciones recibida ya existe en el sistema");}
    }

public void validarAlteracionCantidadOpciones(Integer actual, List<opcionDtoCreate> modificacion) {
        if(!actual.equals(opcionValidators.cantidadOpciones(modificacion))){
            throw           new CantidadOpcionesInvalidException("No esta permitido agregar ni eliminar opciones de las que ya existian");}
}
/**
     * Detectar alteraciones de descripcion y orden de visualizacion de las opciones
     */
public List<opcionDto> validarAlteracionOpcion(Long id, List<Opcion> actual, List<opcionDtoCreate> dto) {
    if (actual == null || dto == null || actual.size() != dto.size()) {
        throw new IllegalArgumentException("La cantidad de opciones no coincide con la configuración actual");
    }

    Map<Short, opcionDtoCreate> dtoPorFila = dto.stream()
            .collect(Collectors.toMap(opcionDtoCreate::fila, Function.identity()));

    List<opcionDtoCreate> aux = new ArrayList<>(actual.size());
    int modificacion = 0;

    for (Opcion x : actual) {
        opcionDtoCreate opcion = dtoPorFila.get(x.getFila());
        if (opcion == null) {
            throw new IllegalArgumentException("No se encontró la fila " + x.getFila() + " en la solicitud");
        }

        boolean cambioOrden = !Objects.equals(x.getOrden(), opcion.orden());
        boolean cambioDescripcion = !TextoUtils.comparar(x.getDescripcion(), opcion.descripcion());

        if (cambioOrden || cambioDescripcion) {
            aux.add(new opcionDtoCreate(x.getFila(), opcion.orden(), opcion.descripcion()));
            modificacion++;
        } else {
            aux.add(new opcionDtoCreate(x.getFila(), x.getOrden(), x.getDescripcion()));
        }
    }

    if (modificacion == 0) {
        return List.of();
    }

    this.validarFormatoOpciones(aux);
    return aux.stream()
            .sorted(Comparator.comparing(
                    opcionDtoCreate::orden,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ))
            .map(o -> new opcionDto(o, id))
            .toList();
}

//    public List<opcionDto> ValidarAlteracionOpcion(Long id,List<Opcion> actual, List<opcionDtoCreate> dto) {
//        List<opcionDtoCreate> aux = new ArrayList<>();
//        int modificacion = 0;
//
//        for (int i = 0; i < actual.size(); i++) {
//            Opcion x = actual.get(i);
//            opcionDtoCreate opcion = dto.get(i);
//            if (x.getOrden() != opcion.orden()
//                    || !TextoUtils.comparar(x.getDescripcion(), opcion.descripcion())) {
//                aux.add(opcion);
//            modificacion++;}
//            else{
//                aux.add(new opcionDtoCreate(actual.get(i).getFila(),x.getOrden(),x.getDescripcion()));
//            }}
//        if(modificacion==0){
//            return new ArrayList<>();}
//
//this.validarFormatoOpciones(aux);
//                   return aux.stream()
//                    .map(o -> new opcionDto(
//                            o,id))
//                    .toList();
//        }
    /**
     *        Detectar modificaciones de la descripcion
     *        Detectar si la cantidad de {@link Opcion} es la misma que la persistida en la bdd durante la creacion
     *        Detectar si el nuevo formato de las opciones  es adecuado
     */
    public boolean validarFormatoModificaciones(OpcionRespuesta actual, opcionRespuestaDtoCreate dto) {
        opcionValidators.filaUnica(dto.opciones());

      boolean descripcion;
        descripcion = this.validarAlteracionTexto(actual.getDescripcion(), dto.descripcion());
        this.validarAlteracionCantidadOpciones(actual.getOpciones().size(), dto.opciones());
        this.validarFormatoOpciones(dto.opciones());
        return descripcion; }
}

    //-----------------------------------------------------------





