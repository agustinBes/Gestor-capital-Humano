package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.interfaces.services.IserviceOpcionRespuesta;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FactorPreguntaMediator extends GenericMediator<IserviceFactor, IservicePregunta> {

    @Override
    public void registrarServicioA(IserviceFactor iserviceFactor) {
        super.registrarServicioA(iserviceFactor);
    }

    @Override
    public void registrarServicioB(IservicePregunta iservicePregunta) {
        super.registrarServicioB(iservicePregunta);
    }

    protected IserviceFactor serviceFactor(){
        return this.servicioA;}

    protected IservicePregunta servicePregunta(){
        return this.servicioB;};

    public Factor obtenerFactor(Long id) {
        return this.serviceFactor().obtenerEntidad(id);
    }
    public List<String> eliminarPreguntas(Long id, Long idConsultor){
   return   servicePregunta().eliminarPorFactor(id,idConsultor);
    }

}
