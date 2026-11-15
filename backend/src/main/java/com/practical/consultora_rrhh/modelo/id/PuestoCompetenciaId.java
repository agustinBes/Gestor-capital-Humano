package com.practical.consultora_rrhh.modelo.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
@Embeddable()
public class PuestoCompetenciaId implements Serializable {
    @Column(name="id_competencia")
    private Long idCompetencia;
     @Column(name="id_puesto")
    private Long idPuesto;

     public PuestoCompetenciaId() {}
    }
