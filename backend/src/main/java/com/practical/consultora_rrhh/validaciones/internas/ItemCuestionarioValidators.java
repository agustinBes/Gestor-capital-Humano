package com.practical.consultora_rrhh.validaciones.internas;

import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreateItems;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;
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
                itemCuestionarioDtoCreateItems nuevo = new itemCuestionarioDtoCreateItems(pregunta, (short) i);
                items.add(new ItemCuestionario(nuevo));
                lista.removeFirst();
            }}
        }
        return items;
    }

    public static void validarItems(Cuestionario actual, List<itemCuestionarioDtoCreate> items) {
  Long identificador = actual.getId();

  for (itemCuestionarioDtoCreate item : items) {
  Long pregunta=item.idpregunta();
  }
    }
}





