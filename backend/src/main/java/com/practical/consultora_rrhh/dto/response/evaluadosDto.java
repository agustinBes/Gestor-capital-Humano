package com.practical.consultora_rrhh.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import com.practical.consultora_rrhh.modelo.enums.tipoDocumento;

/**
 * Formato de los candidatos evaluados para un puesto que se presenta al usuario
 * luego de evaluar candidatos en el CU 25
 * @author  Besigniano Agustin
 * @since  1.0
 */
public record evaluadosDto(
        String apellido,
        String nombre,
        @JsonAlias(value = "tipoDocumento")
        @JsonProperty(value = "tipoDocumento de Documento")
        String tipo,
        @JsonProperty(value = "Numero de Documento")
        @JsonAlias("nroDocumento")
        String nroDocumento,
        @JsonProperty(value = "clave de ingreso")
        @JsonAlias("claveIngreso")
        String claveIngreso
) {

    public evaluadosDto(Candidato c,String clave)
    {
        this(
                c.getApellido(),
                c.getNombre(),
                tipoDocumento.desdeTipo(c.getTipo()),
                c.getDocumento(),
                clave
        );

    }
}
