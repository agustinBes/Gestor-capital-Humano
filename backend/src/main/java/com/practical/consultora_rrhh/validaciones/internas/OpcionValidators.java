package com.practical.consultora_rrhh.validaciones.internas;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.opcionDtoCreate;
import org.springframework.stereotype.Service;
/**
 * Clase para validar atributos de la lista de opciones garantizando
 *      *existencia de un orden consecutivo
 *      *descripcion unica sin importar espacios extras, mayusculas o minusculas
 *      *cantidad de opciones de respuesta para validar durante una modificacion
 *
 * @author  Besigniano Agustin
 * @Since 1.0
 */
import java.util.*;
@Service
public class OpcionValidators {


    public boolean ordenConsecutivo(List<opcionDtoCreate> opciones) {
        Set<Short> ordenes = new HashSet<>();

        for (opcionDtoCreate opcion : opciones) {
            if (!ordenes.add(opcion.orden())) {
                return false;
            }
        }

        for (short i = 1; i <= opciones.size(); i++) {
            if (!ordenes.contains(i)) {
                return false; // Falta algún número
            }
        }
        return true;
    }

    public boolean descripcionUnica(List<opcionDtoCreate> opciones) {
        Set<String> descripciones = new HashSet<>();

        for (opcionDtoCreate opcion : opciones) {
            if (!descripciones.add(TextoUtils.normalizar(opcion.descripcion()))) {
                return false;
            }
        }
        return true;
    }
    public Integer cantidadOpciones(List<opcionDtoCreate> opciones) {
        return opciones.size();
    }


}
