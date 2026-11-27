package com.practical.consultora_rrhh.dao.specifications;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
/**
 * Creacion de filtros de busqueda combinados entre los filtros recibidio de {@link puestoFiltrosDto }
 * @author Besigniano Agustin
 * @since  1.0
 */
public class PuestoSpecifications {
    public static Specification<Puesto> buscar(puestoFiltrosDto dto) {
        return (root, query, cb) -> {

            List<Predicate> predicados = new ArrayList<>();

            if (TextoUtils.tieneContenido(dto.puesto())) {
                predicados.add(
                        TextoSpecifications.buscarTexto(cb, root.get("nombre"), dto.puesto(), false)
                );
            }
            if (TextoUtils.tieneContenido(dto.empresa())) {
                predicados.add(
                        TextoSpecifications.buscarTexto(cb, root.get("empresa"), dto.empresa(), true));
            }
            if (TextoUtils.tieneContenido(dto.codigo())) {
                predicados.add(
                        TextoSpecifications.buscarTexto(cb, root.get("codigo"), dto.codigo(), false));
            }

            return cb.and(predicados.toArray(new Predicate[0]));};}

}