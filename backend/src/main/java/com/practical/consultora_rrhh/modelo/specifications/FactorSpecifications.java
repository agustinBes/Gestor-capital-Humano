package com.practical.consultora_rrhh.modelo.specifications;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Creacion de filtros de busqueda combinados entre los filtros recibidio de {@link preguntaFiltrosDto }
 * @author Besigniano Agustin
 * @since  1.0
 */

public class FactorSpecifications {
    public static Specification<Factor> buscar(String factor, String competencia) {
        return (root, query, cb) -> {

            List<Predicate> predicados = new ArrayList<>();
            if (TextoUtils.tieneContenido(factor)) {
                predicados.add(
                        TextoSpecifications.buscarTexto(cb, root.get("nombre"), factor, true));
            }
//---------------------------------COMPETENCIA-----------------------------------------

            if (TextoUtils.tieneContenido(competencia)) {
                predicados.add(
                        TextoSpecifications.buscarTexto(cb, root.get("competencia").get("nombre"), competencia, true));
            }
            return cb.and(predicados.toArray(new Predicate[predicados.size()]));
        };}
}



