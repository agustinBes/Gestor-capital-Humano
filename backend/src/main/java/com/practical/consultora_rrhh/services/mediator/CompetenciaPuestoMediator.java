package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import org.springframework.stereotype.Component;

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




}
