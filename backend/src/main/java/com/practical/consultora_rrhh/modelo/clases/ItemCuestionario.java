package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.modelo.id.itemCuestionarioId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item_cuestionario")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ItemCuestionario {
@EmbeddedId
private itemCuestionarioId  item;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @MapsId("idCuestionario")
    @JoinColumn(name="id_cuestionario")
private Cuestionario cuestionario;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @MapsId("idPregunta")
    @JoinColumn(name="id_pregunta")
private Pregunta pregunta;
@Column
private short puntaje;
@Column
private Short opcion;
@Column(name = "nro_bloque")
private short NroBloque;

}
