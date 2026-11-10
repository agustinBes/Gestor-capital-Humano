package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.auxiliares.InternalMediator;
import com.practical.consultora_rrhh.interfaces.services.IserviceOpcionRespuesta;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import com.practical.consultora_rrhh.services.ServiceOpcionRespuesta;
import com.practical.consultora_rrhh.services.ServicePregunta;
import org.springframework.stereotype.Component;

/**
 * Implementar el patrón Mediator para centralizar la comunicación entre los servicios
 * de Pregunta {@link ServicePregunta} y OpcionRespuesta {@link ServiceOpcionRespuesta},
 * @see GenericMediator
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */

@Component
public class PreguntaOpcionRMediator extends GenericMediator<IservicePregunta,IserviceOpcionRespuesta>  {

protected IservicePregunta ServicioPregunta(){
    return this.servicioA;
};
protected IserviceOpcionRespuesta ServicioOpcionRespuesta(){
    return this.servicioB;
}


       public boolean opcionRespuestaEnUso(Long id){
        return
                this.ServicioPregunta().existeOpcionRespuestaEnUso(id);
    }
    public OpcionRespuesta obtenerEntidad(Long id){
        return
                this.ServicioOpcionRespuesta().obtenerEntidad(id);
    }




}
