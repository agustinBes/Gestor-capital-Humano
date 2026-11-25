package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import org.springframework.stereotype.Component;

import java.util.List;

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


}
