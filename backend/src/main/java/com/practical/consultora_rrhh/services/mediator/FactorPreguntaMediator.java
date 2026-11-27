package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.interfaces.services.IserviceOpcionRespuesta;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
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
