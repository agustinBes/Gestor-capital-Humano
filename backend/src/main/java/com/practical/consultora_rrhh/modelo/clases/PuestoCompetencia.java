package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.dto.request.puestoCompetenciaDtoCreate;
import com.practical.consultora_rrhh.modelo.id.PuestoCompetenciaId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
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

public PuestoCompetencia(puestoCompetenciaDtoCreate dto){
   puesto=dto.puesto();
   competencia=dto.competencia();
ponderacion= dto.Ponderacion();
}

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
