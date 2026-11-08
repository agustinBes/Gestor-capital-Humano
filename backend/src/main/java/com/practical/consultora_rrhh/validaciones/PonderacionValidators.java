package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.excepciones.especializadas.atributos.SumaPonderacionesInvalidException;
import com.practical.consultora_rrhh.modelo.clases.Opcion;
import com.practical.consultora_rrhh.modelo.clases.Ponderacion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PonderacionValidators {
    public void ValidarPuntaje(List<Short> ponderacion) {
        int sum = 0;
        for (Short puntaje : ponderacion) {
            sum = sum + puntaje;
        }
        if (sum != 10) {
            throw new SumaPonderacionesInvalidException("La suma de las ponderaciones en total debe ser 10");
        }
    }
//---------------------------------------------------------------------------------

    /**
     * Funcion auxiliar  para simplificar la logica del servicio
     * Encargada de construir las ponderaciones entre la opcion y el puntaje a persistir
     */
    public List<Ponderacion> construirPonderaciones(List<Short> ponderacion, List<Opcion> opciones, Long idopcionRespuesta) {
        List<Ponderacion> ponderaciones = new ArrayList<>();

        for (int i = 0; i < opciones.size(); i++) {
            ponderaciones.add(
                    Ponderacion.builder()
                            .fila(opciones.get(i).getFila())
                            .puntaje(ponderacion.get(i))
                            .opcionRespuesta(idopcionRespuesta)
                            .build());
        }
        return ponderaciones;
    }

    //----------------------------------------------------------
    public boolean validarModificacionPonderaciones(List<Ponderacion> actual, List<Short> recibidos) {

        boolean modificacion = false;
        int i = 0;
        while (i < actual.size() && !modificacion) {
            Short puntajeActual = actual.get(i).getPuntaje();
            Short puntajePonderacion = recibidos.get(i);
            if (!puntajeActual.equals(puntajePonderacion)) {
                modificacion = true;
            }
            i++;
        }
        return modificacion;
    }
}