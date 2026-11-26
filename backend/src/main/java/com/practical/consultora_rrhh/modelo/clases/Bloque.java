package com.practical.consultora_rrhh.modelo.clases;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bloque {
    private List<ItemCuestionario> itemCuestionario;
   private  short numeroBloque;
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
    public Bloque(Bloque original) {
        this.numeroBloque = original.numeroBloque;
        this.idCuestionario = original.idCuestionario;
        this.completado = original.completado;

        this.itemCuestionario = original.itemCuestionario.stream()
                .map(ItemCuestionario::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
