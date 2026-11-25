package com.practical.consultora_rrhh.validaciones;

import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CompetenciaInvalidaCuestionarioException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CuestionarioActivoException;
import com.practical.consultora_rrhh.interfaces.dao.IcuestionarioDao;
import com.practical.consultora_rrhh.modelo.clases.*;
import com.practical.consultora_rrhh.modelo.propiedades.CuestionarioProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CuestionarioValidators {
private final IcuestionarioDao dao;
private final CuestionarioProperties configuraciones;

    public CuestionarioValidators(IcuestionarioDao dao, CuestionarioProperties configuraciones) {
        this.dao = dao;
        this.configuraciones = configuraciones;
    }
//    public List<Cuestionario> recuperarCuestionarioss(List<Long> identificadores){
//        List<Cuestionario> activos=dao.cuestionariosActivos(identificadores);
//        //realizar proceso de actualizacion
//    }

    public void ValidarCuestionariosActivos(List<Long> identificadores){
        List<Cuestionario> activos=dao.cuestionariosActivos(identificadores);

      //  List<Cuestionario> activos=recuperarCuestionarioss(identificadores);
        LocalDateTime ahora=LocalDateTime.now();


        if(!activos.isEmpty()){
List<Candidato>  conflicto=activos.stream().map(Cuestionario::getCandidato).toList();
            List<String> personas=conflicto.stream()
                    .map(o ->"Nro Candidato: "+ o.getId().toString()+" nombre: "+o.getApellido()+","+o.getNombre()+"\n")
                    .toList();
            throw new CuestionarioActivoException("Se encontraron candidatos con cuestionarios activos"+"\n"+personas);
        }
    }
    public List<Pregunta> validarPreguntas(List<Competencia> competencias, List<Pregunta> preguntas) {

        List<Pregunta> preguntasValidadas = new ArrayList<>();
        Set<Competencia> competenciaValidada = new HashSet<>();
List<String> competenciasInvalidas = new ArrayList<>();

        for (Competencia c : competencias) {
            for (Factor f : c.getFactores()) {
                List<Pregunta> resultado =
                        preguntas.stream()
                                .filter(o -> o.getFactor().equals(f))
                               .toList();
                if (!resultado.isEmpty() && resultado.size() > 1) {
                       competenciaValidada.add(c);
                       preguntasValidadas.addAll(resultado);
                }
            }
            if(!competenciaValidada.contains(c)){
                competenciasInvalidas.add(c.getNombre());}

            }
//        competenciaValidada.forEach(competencia -> {
//            System.out.println("Competencia: " + competencia.getNombre()
//                    + " | ID: " + competencia.getId());
//
//            competencia.getFactores().forEach(factor -> {
//                System.out.println("    Factor: " + factor.getNombre()
//                        + " | ID: " + factor.getId());
//            });
//        });

      if(!competenciasInvalidas.isEmpty()) {
          throw new CompetenciaInvalidaCuestionarioException(HttpStatus.UNPROCESSABLE_CONTENT,"Se encontraron competencias aplicadas al puesto que no cumplen los requisitos" + competenciasInvalidas.toString());
      }

      return preguntasValidadas;

        }
public    List<Pregunta> generarPreguntas(List<Competencia> competencias,List<Pregunta> preguntas) {
   Map<Factor,List<Pregunta>> preguntasPorFactor=new HashMap<>();

    for (Competencia c : competencias) {
        for (Factor f : c.getFactores()) {
            List<Pregunta> aux = preguntas.stream()
                    .filter(o -> o.getFactor().equals(f))
                    .collect(Collectors.toList());
            if (!aux.isEmpty() && aux.size() > 1) {
                Collections.shuffle(aux);

                preguntasPorFactor.put(f, new ArrayList<>( aux.subList(0,1)));
            }
        }
    }

    return preguntasPorFactor.values().stream().flatMap(List::stream).collect(Collectors.toList());
}}


