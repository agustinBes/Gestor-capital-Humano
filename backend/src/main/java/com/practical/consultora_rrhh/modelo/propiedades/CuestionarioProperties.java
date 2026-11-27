package com.practical.consultora_rrhh.modelo.propiedades;

import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/**
 * Archivo de configuracion que recupera de 'application.properties' los
 * parametros con los que se diseñan los cuestionarios
 * cantidad de accesos
 * la cantidad de preguntas por bloque
 * el tiempo maximo que puede permanecer en el estado activo
 * el tiempo maximo de realizacion del cuestionario
 */
@Configuration
@ConfigurationProperties(prefix = "cuestionario")
@Validated
@Getter
@Setter
public class CuestionarioProperties {
    private Duration tiempoMaximoActivo;
    private Duration tiempoMaximoRealizacion;
    private Integer preguntasPorBloque;
    private String instrucciones;
    private Integer cantidadDeAccesos;
    @AssertTrue(message = "La cantidad de preguntas por bloque debe ser un número par mayor que cero")
    public boolean isPreguntasPorBloqueValido() {
        return preguntasPorBloque != null
                && preguntasPorBloque > 0
                && preguntasPorBloque % 2 == 0;
    }
    public short getPreguntasPorBloque() {
        return preguntasPorBloque.shortValue();
    }
    public short getCantidadDeAccesos() {
        return cantidadDeAccesos.shortValue();
    }
}


