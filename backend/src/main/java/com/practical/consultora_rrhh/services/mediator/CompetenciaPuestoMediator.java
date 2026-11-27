package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
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
public class CompetenciaPuestoMediator extends  GenericMediator<IserviceCompetencia,IservicePuesto> {
    @Override
    public void registrarServicioA(IserviceCompetencia iserviceCompetencia) {
        super.registrarServicioA(iserviceCompetencia);
    }

    @Override
    public void registrarServicioB(IservicePuesto iservicePuesto) {
        super.registrarServicioB(iservicePuesto);
    }

    protected IserviceCompetencia serviceCompetencia(){
        return servicioA;
    }
    protected IservicePuesto servicePuesto(){
        return servicioB;
    }
public boolean CompetenciaEnUso(Long idCompetencia){
        return servicePuesto().existeCompetenciaEnUso(idCompetencia);
}

public List<Competencia> validarCompetencias(List<Long> idCompetencias){
        return  serviceCompetencia().validarCompetencias(idCompetencias);
}



}
