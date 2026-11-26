package com.practical.consultora_rrhh.validaciones.internas;

import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreateItems;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.BloqueCompletadoException;
import com.practical.consultora_rrhh.excepciones.genericas.ColeccionCorruptaException;
import com.practical.consultora_rrhh.excepciones.genericas.IntegridadComprometidaException;
import com.practical.consultora_rrhh.modelo.clases.*;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase que valida y genera items de cuestionario en el formato a persistir para la bdd
 */
public class ItemCuestionarioValidators {

    /**
     * Genera todos los items asociados a un cuestionario que debe ser guardados
     * @param bloques Determina la cantidad de bloques que requiere el cuestionario
     * @param preguntas Lista de preguntas generadas y validadas para cada candidato en base al puesto y los criterios de la competencia
     * @param preguntasxBloque estable cuantas items entran en cada bloque
     * @return lista de items para añadir al crear al cuestionario
     */
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

    /**
     * Validamos la integridad referencial entre cada item con cada pregunta y bloque asociado
     * Obtenemos la opcion de respuesta
     * validamos que la opcion elegida exista para esa configuracion de Opciones
     * Solo proceso de validacion
     * @param actual recibimos el cuestionario persistido en la bdd
     * @param items recibimos la lista de items que contesto el usuario
     */
    public static void validarItems(Cuestionario actual, List<itemCuestionarioDtoCreate> items) {

  for (itemCuestionarioDtoCreate item : items) {
      Long pregunta=item.idpregunta();
      short bloque=item.NroBloque();
      short fila=item.opcion();

List<ItemCuestionario> ic=actual.getItems().stream()
         .filter(o->o.getNroBloque()==bloque)
         .filter(o->o.getPregunta().getId().equals(pregunta))
         .toList();

if(ic.size() != 1) {

    throw new IntegridadComprometidaException("Se encuentran items que no corresponden al cuestionario");
}

OpcionRespuesta aux=ic.getFirst().getPregunta().getOpcionRespuesta();

List<Opcion> opciones=aux.getOpciones();

boolean existe=opciones.stream().
        anyMatch(o->o.getFila()==fila);


if(!existe) {
    throw new IntegridadComprometidaException("La opcion de respuesta enviada no existe en el sistema");
}


  }
    }

    /**
     * actualizar items respondidos
     * @param bloque  Estructura auxiliar construida a partir del cuestionario de la bdd
     * @param items   items contestados por el usuario
     */

    public static List<Bloque> actualizarItems(List<Bloque> bloque, List<itemCuestionarioDtoCreate> items) {
        List<Bloque> copia = bloque.stream()
                .map(Bloque::new)
                .collect(Collectors.toCollection(ArrayList::new));

        for(itemCuestionarioDtoCreate item : items) {
      Short nroBloque=item.NroBloque();
      List<Bloque> aux=copia.stream()
              .filter(o-> o.getNumeroBloque()==nroBloque)
              .toList();

      if(aux.size()!=1) {
          throw  new IntegridadComprometidaException(  "La estructura interna del cuestionario presenta bloques inexistentes o duplicados");

      }
Bloque actual=aux.getFirst();

      if(actual.isCompletado()){
          throw new BloqueCompletadoException("No se puede modificar una pregunta ya contestada", HttpStatus.CONFLICT);
      }

      Long pregunta=item.idpregunta();
      Short opcion=item.opcion();

            actual.getItemCuestionario().stream()
                    .filter(o -> o.getPregunta().getId().equals(pregunta))
                    .findFirst()
                    .ifPresent(o -> {
                        short puntaje = o.getPregunta()
                                .getPonderaciones()
                                .get(opcion)
                                .getPuntaje();

                        o.setOpcion(opcion);
                        o.setPuntaje(puntaje);
                    });

 }
  copia.forEach(Bloque::completado);
return  copia;}

}





