package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.interfaces.auxiliares.IMediatorGeneric;
import com.practical.consultora_rrhh.interfaces.services.InternalService;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Clase abstracta
 * Mediador  genérico para desacoplar y gestionar dependencias circulares
 * entre dos servicios mediante registro posterior a su construcción.
 *
 * @param <servicioA> Tipo del primer servicio participante, asociado a {@link #registrarServicioA(Object)}.
 * @param <servicioB> Tipo del segundo servicio participante, asociado a {@link #registrarServicioB(Object)}.
 * @author  Besigniano Agustin
 * @since 1.0
 *  */

public abstract class GenericMediator<servicioA,servicioB> implements IMediatorGeneric<servicioA,servicioB> {

  protected servicioA servicioA;
    protected servicioB servicioB;
    public void registrarServicioA(servicioA servicioA) {
this.servicioA = servicioA;
    }

    public void registrarServicioB(servicioB servicioB) {
this.servicioB = servicioB;
    }


}
