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
@NoArgsConstructor
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
private short opcion;
@Column(name = "nro_bloque")
private short NroBloque;



public ItemCuestionario(itemCuestionarioDtoCreateItems dto){
    puntaje=dto.Puntaje();
    pregunta=dto.pregunta();
    opcion=0;
    NroBloque=dto.NroBloque();
    cuestionario=null;
    item=null;
}

    public ItemCuestionario(ItemCuestionario original) {
        this.item = new itemCuestionarioId(original.getCuestionario().getId(),original.getPregunta().getId());
        this.cuestionario = original.getCuestionario();
        this.pregunta = original.getPregunta();
        this.puntaje = original.getPuntaje();
        this.opcion = original.getOpcion();
        this.NroBloque = original.getNroBloque();
    }
}
