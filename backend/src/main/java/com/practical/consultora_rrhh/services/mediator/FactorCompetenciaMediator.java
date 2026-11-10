package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Component;

@Component
public class FactorCompetenciaMediator extends GenericMediator<IserviceFactor, IserviceCompetencia> {

protected IserviceFactor  serviceFactor(){
    return this.servicioA;
}
protected IserviceCompetencia  serviceCompetencia(){
    return this.servicioB;
}

public Competencia obtenerCompetencia(Long id){
    return  this.serviceCompetencia().obtenerEntidad(id);
}
public Factor obtenerFactor(Long id){
    return this.serviceFactor().obtenerEntidad(id);
}
/*
public boolean FactorEnUso(Long id){
    return this.serviceCompetencia()
}
public boolean competenciaEnUso(Long id){
    return this.serviceFactor().
}*/

}
