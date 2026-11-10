package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.interfaces.services.IserviceOpcionRespuesta;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Component;

@Component
public class FactorPreguntaMediator extends GenericMediator<IserviceFactor, IservicePregunta> {


    protected IserviceFactor serviceFactor(){
        return this.servicioA;}

    protected IservicePregunta servicePregunta(){
        return this.servicioB;};

    public Factor obtenerFactor(Long id) {
        return this.serviceFactor().obtenerEntidad(id);
    }
    public void eliminarPreguntas(Long id){
        this.servicePregunta().eliminarPorFactor(id);
    }

}
