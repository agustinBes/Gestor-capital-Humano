package com.practical.consultora_rrhh.dao.specifications;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Creacion de filtros de busqueda combinados entre los filtros recibidio de {@link candidatoFiltrosDto }
 * @author Besigniano Agustin
 * @since  1.0
 */

public class CandidatoSpecifications {
    public static Specification<Candidato> buscar(candidatoFiltrosDto dto) {
        return (root, query, cb) -> {

            List<Predicate> predicados = new ArrayList<>();
            if (dto.id() != null) {
                predicados.add(cb.equal(root.get("id"), dto.id()));}
                if (TextoUtils.tieneContenido(dto.nombre())) {
                    predicados.add(
                            TextoSpecifications.buscarTexto(cb, root.get("nombre"), dto.nombre(), true));
                }
                if (TextoUtils.tieneContenido(dto.apellido())) {
                    predicados.add(
                            TextoSpecifications.buscarTexto(cb, root.get("apellido"), dto.apellido(), true));
                }
                predicados.add(cb.isFalse(root.get("eliminacion")));

                return cb.and(predicados.toArray(new Predicate[0]));
            }  ;
           }}



