package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.interfaces.services.IserviceOrdenMerito;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;
import org.springframework.stereotype.Component;

import java.util.List;
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
