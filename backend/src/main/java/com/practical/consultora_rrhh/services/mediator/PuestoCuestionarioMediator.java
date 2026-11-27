package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
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
public class PuestoCuestionarioMediator extends GenericMediator<IservicePuesto, IserviceCuestionario>{

protected IservicePuesto servicePuesto(){
    return servicioA;
    }
    protected IserviceCuestionario serviceCuestionario(){
    return servicioB;
    }

    public List<PuestoCompetencia> obtenerPorPuesto(Long id){
    return servicePuesto().obtenerPorPuesto(id);
    }

public boolean existePuestoEnUso(Long id){
    return serviceCuestionario().existePuestoEnUso(id);
}
}
