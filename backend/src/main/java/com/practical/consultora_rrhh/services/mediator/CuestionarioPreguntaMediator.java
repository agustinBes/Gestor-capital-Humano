package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * Clase que hereda de la clase abstracta {@link GenericMediator}
 * establece un metodo para que se registren los servicios que requieren interactuar
 entre si con el fin de evitar dependencias circulares
 Se implementan los metodos requeridos en la comunicacion con otro servicio
 se evita interactuar con la capa de persistencia
 y se evita la comunicacion directa con otro servicio
 @author  Besigniano Agustin
 @since 1.0
 */
@Component
public class CuestionarioPreguntaMediator extends GenericMediator<IservicePregunta, IserviceCuestionario> {

    protected IserviceCuestionario serviceCuestionario(){
        return  servicioB;
    }
    protected IservicePregunta  servicePregunta(){
        return  servicioA;
    }

    public List<Pregunta> obtenerPreguntasPorCompetencias(List<Competencia> competencias){
        return servicePregunta().obtenerPorCompetencia(competencias);
    }

}
