package com.practical.consultora_rrhh.modelo.specifications;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PreguntaSpecifications {
    public static Specification<Pregunta> buscar(
            preguntaFiltrosDto filtro) {
        return (root, query, cb) -> {

            List<Predicate> predicados=new ArrayList<>();
            if(filtro.pregunta()!=null && !filtro.pregunta().isBlank()){

                String pattern = "%" + TextoUtils.normalizar(filtro.pregunta()).toLowerCase() + "%";

                Predicate matchTitulo = cb.like(cb.lower(root.get("pregunta")), pattern);
            predicados.add(matchTitulo);
            }
            if(filtro.competencia()!=null && !filtro.competencia().isBlank()){
                String pattern = "%" + TextoUtils.normalizar(filtro.competencia()).toLowerCase() + "%";
                Predicate matchTitulocompetencia = cb.equal(cb.lower(root.get("factor").get("competencia").get("nombre")), pattern);
                Predicate matchCodigocompetencia=cb.equal(cb.lower(root.get("factor").get("competencia").get("codigo")), pattern);

            predicados.add(cb.or(matchTitulocompetencia,matchCodigocompetencia));
            }
            if(filtro.factor()!=null && !filtro.factor().isBlank()){
                String pattern = "%" + TextoUtils.normalizar(filtro.factor()).toLowerCase() + "%";
                Predicate matchTitulofactor = cb.equal(cb.lower(root.get("factor").get("nombre")), pattern);
                Predicate matchCodigofactor =cb.equal(cb.lower(root.get("factor").get("codigo")), pattern);


                predicados.add(cb.or(matchTitulofactor,matchCodigofactor));
            }
            predicados.add(cb.isFalse(root.get("eliminacion")));
// EL "INNER JOIN" LÓGICO:
            // Si llenaste pregunta y competencia, la base de datos exige que AMBAS cosas
            // ocurran en la misma fila para devolverla. Si dejas uno en null, se ignora ese filtro.
            return cb.and(predicados.toArray(new Predicate[0]));
        };
}
}
