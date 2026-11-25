package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import org.springframework.stereotype.Component;

import java.util.List;

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
