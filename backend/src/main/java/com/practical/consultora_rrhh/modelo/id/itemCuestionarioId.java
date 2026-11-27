package com.practical.consultora_rrhh.modelo.id;

import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *Conforma la clave primaria de la entidad {@link ItemCuestionario}
 * Su existencia esta sujeta a la de un cuestionario por lo que no es una entidad fuerte
 * @author Besigniano Agustin
 * @since 1.0
 */
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
