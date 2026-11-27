package com.practical.consultora_rrhh.modelo.id;

import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
/**
 *Conforma la clave primaria de la entidad {@link PuestoCompetencia}
 * Su existencia esta sujeta a la de un puesto
 * @author Besigniano Agustin
 * @since 1.0
 */

@Embeddable()
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class PuestoCompetenciaId implements Serializable {
    @Column(name="id_competencia")
    private Long idCompetencia;
     @Column(name="id_puesto")
    private Long idPuesto;

     public PuestoCompetenciaId() {}
    }
