package com.practical.consultora_rrhh.validaciones.comunes;

import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CodigoDuplicadoException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.NombreDuplicadoException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.NroOrdenDuplicadoException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.PreguntaDuplicadaException;
import com.practical.consultora_rrhh.modelo.enums.tipo_campo_duplicado;

public abstract class DuplicadoValidators {


    public void validarCampoDuplicado(String valor, boolean result, tipo_campo_duplicado tipo) {
        if (result) {

            switch (tipo) {
                case NOMBRE->
                    throw new NombreDuplicadoException("Ya existe el nombre: "+valor + " en el sistema");
                case PREGUNTA->
                    throw new PreguntaDuplicadaException("Ya existe la pregunta: " + valor + " asociada a ese factor y competencia");
               case CODIGO->
                    throw new CodigoDuplicadoException("Ya existe el codigo: " + valor + " en el sistema");
                case Nombre_Pregunta->
                    throw new NombreDuplicadoException("Ya existe el nombre: " + valor + " asociada a ese factor y competencia");
                case NRO_ORDEN ->
                    throw new NroOrdenDuplicadoException("Ya existe el numero de orden: "+valor+" asociado a esta competencia");
            }
        }


    }
}
