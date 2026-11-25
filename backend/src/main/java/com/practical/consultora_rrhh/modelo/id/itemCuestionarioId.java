package com.practical.consultora_rrhh.modelo.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable()
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class itemCuestionarioId implements Serializable {
    @Column(name="id_cuestionario")
    private Long idCuestionario;
     @Column(name="id_pregunta")
    private Long idPregunta;

     public itemCuestionarioId() {}
    }
