package com.practical.consultora_rrhh.interfaces.auxiliares;

/**
 * Interfaz que define el registro de los servicios
 * que deban interactuar con otro servicio a traves de un mediador
 * para evitar dependencia circulares
 * @author  Besigniano Agustin
 * @since 1.0
 * @param <servicioA> parametro que representa al servicio A o 1
 * @param <servicioB>  parametro que representa al servicio B o 2
 */
public interface IMediatorGeneric <servicioA,servicioB>{

void registrarServicioA(servicioA servicioA);
void registrarServicioB(servicioB servicioB);


}
