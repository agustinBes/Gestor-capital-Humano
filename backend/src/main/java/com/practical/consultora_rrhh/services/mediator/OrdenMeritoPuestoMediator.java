package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.interfaces.services.IserviceOrdenMerito;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
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
