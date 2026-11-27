package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCandidato;
import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
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
public class CandidatoCuestionarioMediator extends GenericMediator<IserviceCuestionario, IserviceCandidato> {

protected IserviceCandidato serviceCandidato(){
    return servicioB;
}
protected IserviceCuestionario serviceCuestionario(){
    return servicioA;
}


    public List<Candidato> obtenerCandidatos(List<Long> candidato) {

return  serviceCandidato().obtenerCandidatos(candidato);
    }
}
