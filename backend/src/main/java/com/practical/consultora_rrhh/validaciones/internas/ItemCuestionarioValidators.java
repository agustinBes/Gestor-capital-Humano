package com.practical.consultora_rrhh.validaciones.internas;

import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreate;
import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;

import java.util.*;

public class ItemCuestionarioValidators {

    public static List<ItemCuestionario> generar(Short bloques, List<Pregunta> preguntas, short preguntasxBloque) {
        List<ItemCuestionario> items = new ArrayList<>();
        List<Pregunta> preguntasAux=new ArrayList<>(preguntas);
        Collections.shuffle(preguntasAux);
        List<Pregunta> lista=new ArrayList<>(preguntasAux);
int i=1;
        for(;i<=bloques;i++) {
            int j = 0;

            for (; j < preguntasxBloque; j++) {

if(!lista.isEmpty()){
                Pregunta pregunta = lista.getFirst();
                itemCuestionarioDtoCreate nuevo = new itemCuestionarioDtoCreate(pregunta, (short) i);
                items.add(new ItemCuestionario(nuevo));
                lista.removeFirst();
            }}
        }
        return items;
    }

}





