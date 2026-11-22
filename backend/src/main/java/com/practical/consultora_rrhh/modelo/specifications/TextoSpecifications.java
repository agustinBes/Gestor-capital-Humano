package com.practical.consultora_rrhh.modelo.specifications;

import com.practical.consultora_rrhh.Util.TextoUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase que proporciona métodos de búsqueda sobre campos de texto,
 * con el objetivo de mejorar la precisión y flexibilidad de los resultados.
 *
 * JpaRepository permite realizar búsquedas ignorando diferencias entre
 * mayúsculas y minúsculas, así como establecer coincidencias parciales.
 * Sin embargo, no ofrece de forma directa un mecanismo para ignorar
 * diferencias en el uso de acentos.
 *
 * Mediante el uso de extensiones de Spring Data JPA, se amplían las
 * posibilidades de búsqueda, aumentando la probabilidad de que el usuario
 * encuentre los resultados esperados.
 *
 * Estas extensiones utilizan:
 * - Spring Data JPA Specifications (Specifications).
 * - JPA Criteria API mediante CriteriaBuilder para construir
 *   consultas dinámicas.
 * @author  Besigniano Agustin
 * @since 1.0
 */
public class TextoSpecifications {

    /**Busca coincidencias en la base de datos sobre campos de tipo String,
     * @param atributo Establece el atributo sobre el que vamos a buscar
     * @param texto El texto a buscar se normaliza durante la construccion del predicado
     * @param parcial Establece si el metodo considera busquedas exactas o parciales
     */
    public static <T> Specification<T> coincidencia(String atributo, String texto, boolean parcial,boolean eliminados) {
        return (root, query, cb)->{
        Predicate p=buscarTexto(cb, root.get(atributo), texto, parcial);

            return eliminados
                    ? cb.and(p, cb.isFalse(root.get("eliminacion")))
                    : p;
    };}

    /**
     *   Las búsquedas ignoran las diferencias entre mayúsculas y minúsculas.
     *   Las búsquedas ignoran las diferencias en el uso de acentos,
     *     permitiendo encontrar coincidencias independientemente de cómo
     *     hayan sido escritos.
       *
     * @param parcial establece si la búsqueda admite coincidencias parciales
     *                o requiere coincidencias exactas.
     */
      public static Predicate buscarTexto(
            CriteriaBuilder cb, Expression<String> campo, String texto, boolean parcial) {
        String normalizado = TextoUtils.normalizar(texto).toLowerCase();
        String patron = parcial ? "%" + normalizado + "%" : normalizado;

        Expression<String> campoNormalizado = cb.function(
                "unaccent", String.class, cb.lower(campo));

        Expression<String> patronNormalizado = cb.function(
                "unaccent", String.class, cb.lower(cb.literal(patron)));

        return cb.like(campoNormalizado, patronNormalizado);
    }}