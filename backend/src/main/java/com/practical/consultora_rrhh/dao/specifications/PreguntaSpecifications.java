package com.practical.consultora_rrhh.dao.specifications;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

/**
 * Creacion de filtros de busqueda combinados entre los filtros recibidio de {@link preguntaFiltrosDto }
 * @author Besigniano Agustin
 * @since  1.0
 */

public class PreguntaSpecifications {
    public static Specification<Pregunta> buscar(preguntaFiltrosDto filtro) {
        return (root, query, cb) -> {

            List<Predicate> predicados = new ArrayList<>();
//---------------------------------PREGUNTA-----------------------------------------
            if (TextoUtils.tieneContenido(filtro.pregunta())) {
                predicados.add(
                        TextoSpecifications.buscarTexto(cb, root.get("nombre"), filtro.pregunta(), false));
            }
//---------------------------------COMPETENCIA-----------------------------------------

            if (TextoUtils.tieneContenido(filtro.competencia())) {

                Path<?> competencia = root.get("factor").get("competencia");

                predicados.add(
                        buscarNombreOCodigo(cb, competencia, filtro.competencia()));}
//---------------------------------FACTOR-----------------------------------------

            if (TextoUtils.tieneContenido(filtro.factor())) {
                Path<?> factor = root.get("factor");
                predicados.add(
                        buscarNombreOCodigo(cb, factor, filtro.factor()));}

predicados.add(cb.isFalse(root.get("eliminacion")));

            return cb.and(predicados.toArray(new Predicate[0]));};}


    /**
     * Buscar por nombre o codigo para el factor y la competencia
     */
    private static Predicate buscarNombreOCodigo(
            CriteriaBuilder cb,Path<?> entidad,String texto) {

        Predicate nombre = TextoSpecifications.buscarTexto(cb,entidad.get("nombre"),texto,false);

        Predicate codigo=TextoSpecifications.buscarTexto(cb,entidad.get("codigo"),texto,false);

        return cb.or(nombre, codigo);}



    public static Specification<Pregunta> existeNombre(String nombre,Long idFactor,Long idCompetencia) {
        return (root, query, cb) -> {

            List<Predicate> predicados = new ArrayList<>();
            predicados.add(
                    TextoSpecifications.buscarTexto(cb,root.get("nombre"),nombre,false));


            Predicate p = cb.equal(root.get("factor").get("id"), idFactor);
            Predicate c = cb.equal(root.get("factor").get("competencia").get("id"), idCompetencia);
            predicados.add(p);
            predicados.add(c);
            predicados.add(cb.isFalse(root.get("eliminacion")));
            return cb.and(predicados.toArray(new Predicate[0]));
        };}
    public static Specification<Pregunta> existePregunta(String nombre,Long idFactor,Long idCompetencia) {
        return (root, query, cb) -> {

            List<Predicate> predicados = new ArrayList<>();
            predicados.add(
                    TextoSpecifications.buscarTexto(cb,root.get("pregunta"),nombre,false));

            Predicate p = cb.equal(root.get("factor").get("id"), idFactor);
            Predicate c = cb.equal(root.get("factor").get("competencia").get("id"), idCompetencia);
            predicados.add(p);
            predicados.add(c);
            predicados.add(cb.isFalse(root.get("eliminacion")));

            return cb.and(predicados.toArray(new Predicate[0]));
        };}


}
