package com.practical.consultora_rrhh.modelo.clases;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable()
@Builder
public class Ponderacion{

    @Column(name = "id_opcion_respuesta")
    private Long opcionRespuesta;

    @Column()
    private short fila;

    @Column(name="ponderacion")
    private short puntaje;


}
