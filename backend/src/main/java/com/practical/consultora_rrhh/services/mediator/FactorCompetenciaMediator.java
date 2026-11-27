package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import org.springframework.stereotype.Component;
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
public class FactorCompetenciaMediator extends GenericMediator<IserviceFactor, IserviceCompetencia> {

    @Override
    public void registrarServicioA(IserviceFactor iserviceFactor) {
        super.registrarServicioA(iserviceFactor);
    }

    @Override
    public void registrarServicioB(IserviceCompetencia iserviceCompetencia) {
        super.registrarServicioB(iserviceCompetencia);
    }

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
    public boolean competenciaEnUso(Long id) {
        return this.serviceFactor().validarCompetenciaEnUso(id);
    }

/*
public boolean FactorEnUso(Long id){
    return this.serviceCompetencia()
}

}*/

}
