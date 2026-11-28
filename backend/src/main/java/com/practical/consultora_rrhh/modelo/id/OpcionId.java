package com.practical.consultora_rrhh.modelo.id; // ajustá al paquete de tu proyecto

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OpcionId implements Serializable {

    @Column(name = "id_opcion_respuesta")
    private Long idOpcionRespuesta;

    @Column(name = "fila")
    private short fila;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpcionId that)) return false;
        return fila == that.fila && Objects.equals(idOpcionRespuesta, that.idOpcionRespuesta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOpcionRespuesta, fila);
    }
}