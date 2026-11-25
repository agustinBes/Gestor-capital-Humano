package com.practical.consultora_rrhh.modelo.clases;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bloque {
    private List<ItemCuestionario> itemCuestionario;
   private  Integer numeroBloque;
    private Long idCuestionario;
    boolean completado;

public void completado(){

    short item=(short)itemCuestionario.size();

    short  completados=(short)itemCuestionario.stream()
            .filter(o->o.getOpcion()!=0)
            .count();

if(completados==item){
    completado=true;
}}

}
