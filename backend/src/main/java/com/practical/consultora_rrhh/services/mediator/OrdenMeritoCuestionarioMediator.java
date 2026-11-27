package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.interfaces.services.IserviceOrdenMerito;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;
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
public class OrdenMeritoCuestionarioMediator extends GenericMediator<IserviceOrdenMerito, IserviceCuestionario> {


protected  IserviceOrdenMerito serviceOrdenMerito(){
    return  servicioA;
}
protected  IserviceCuestionario serviceCuestionario(){
    return  servicioB;
}

public List<Cuestionario> obtenerPorPuestos(List<Long> puestos){
    return   serviceCuestionario().obtenerPorPuestos(puestos);

}
public List<Cuestionario> obtenerPorPuesto(Long id){
    return serviceCuestionario().listarPorPuesto(id);
}

    public List<Cuestionario> obtenerCuestionarios(List<Long> cuestionarios) {
    return serviceCuestionario().obtenerxIdsCuestionario(cuestionarios);    }
}
