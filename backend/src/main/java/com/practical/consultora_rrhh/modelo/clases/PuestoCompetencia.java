package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.modelo.id.PuestoCompetenciaId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "puesto_competencia")
public class PuestoCompetencia {
@EmbeddedId
    PuestoCompetenciaId id;

@ManyToOne(fetch = FetchType.LAZY,optional = false)
@MapsId("idPuesto")
@JoinColumn(name="id_puesto")
    private Puesto puesto;


@ManyToOne(fetch = FetchType.LAZY,optional = false)
@MapsId("idCompetencia")
@JoinColumn(name = "id_competencia")
    private Competencia competencia;
@Column
    private short ponderacion;

}
