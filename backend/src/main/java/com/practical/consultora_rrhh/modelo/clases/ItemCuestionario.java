package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreateItems;
import com.practical.consultora_rrhh.modelo.id.itemCuestionarioId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="item_cuestionario")
@Getter
@Setter
@Builder
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



public ItemCuestionario(itemCuestionarioDtoCreateItems dto){
    puntaje=dto.Puntaje();
    pregunta=dto.pregunta();
    opcion=null;
    NroBloque=dto.NroBloque();
    cuestionario=null;
    item=null;
}
}
