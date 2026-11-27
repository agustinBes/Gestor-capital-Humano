package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.interfaces.services.IserviceOrdenMerito;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public class OrdenMeritoPuestoMediator extends  GenericMediator<IserviceOrdenMerito, IservicePuesto> {




    protected   IserviceOrdenMerito serviceOrdenMerito(){
        return servicioA;
    }
    protected   IservicePuesto servicePuesto(){
        return servicioB;
    }

   public Map<Puesto,List<PuestoCompetencia>> obtenerPuestos(puestoFiltrosDto dto){
  return       servicePuesto().buscarParaOrdenMerito(dto);}

    public List<PuestoCompetencia> obtenerCompetencias(Long idPuesto){
        return  servicePuesto().obtenerPorPuesto(idPuesto);
    }

}
