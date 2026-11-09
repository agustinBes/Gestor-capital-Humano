package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.interfaces.dao.IPreguntaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.preguntaRepository;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.modelo.clases.Ponderacion;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import com.practical.consultora_rrhh.modelo.specifications.PreguntaSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 Implementación de la interfaz DAO para operaciones CRUD y consultas
 * personalizadas sobre la entidad {@link Pregunta}.
 *
 * <p>Se abstrae el acceso a datos mediante
 * <a href="https://spring.io/projects/spring-data-jpa#overview">
 * Spring Data JPA</a>, delegando las operaciones de persistencia y busqueda
 * en su correspondiente Repository.</p>
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */


@Repository
public class PreguntaDaoImpl  implements IPreguntaDao {

    private final preguntaRepository repository;

    public PreguntaDaoImpl(preguntaRepository repository) {

        this.repository = repository;
    }

    /**
     * Construir filtros mediante la interfaz {@link Specification} proporcionada
     * por Spring Data para realizar consultas combinadas de múltiples criterios.
     * Retornar los resultados obtenidos.
     */
    public List<Pregunta> buscar(preguntaFiltrosDto dto)
    {
        Specification<Pregunta> filtros= PreguntaSpecifications.buscar(dto);
        return repository.findAll(filtros);

    }

    //-----------------------------------------------------------------------------------------------
    /**
     * Detectar la existencia en el atributo de título(o nombre) en la entidad {@link Pregunta},
     * asociado a un factor y una competencia determinados.
     * Lanzar una excepción en caso de detectar una coincidencia.
     */
    public void existeNombrePorFactorInsensible(String nombre, Long idFactor, Long idCompetencia) {
        repository.existeNombrePorFactorCompetenciaInsensible(nombre, idFactor, idCompetencia);
    }

    /**
     * Detectar la existencia en el atributo  pregunta de la entidad {@link Pregunta},
     * asociado a un factor y una competencia determinados.
     * Lanzar una excepción en caso de detectar una coincidencia.
     */
    public void existePreguntaPorFactorInsensible(String nombre, Long idFactor, Long competencia) {
         repository.existePreguntaPorFactorCompetenciaInsensible(nombre, idFactor, competencia);
    }


    public Pregunta guardar(Pregunta pregunta) {
        return repository.save(pregunta);
    }

    /**
     * Detectar los campos modificados
     * Actualizar la entidad(Dentro de un contexto transaccional)
     */

    @Transactional
    public Pregunta actualizar(Pregunta pregunta, Map<String, String> modificaciones, OpcionRespuesta respuesta, List<Ponderacion> ponderaciones) {
        if (modificaciones != null && !modificaciones.isEmpty()) {
            if (modificaciones.containsKey("nombre")) {
                pregunta.setNombre(modificaciones.get("nombre"));
            }
            if (modificaciones.containsKey("pregunta")) {
                pregunta.setPregunta(modificaciones.get("pregunta"));
            }
            if (modificaciones.containsKey("descripcion")) {
                pregunta.setDescripcion(modificaciones.get("descripcion"));
            }
        }
        if (respuesta != null) {
            pregunta.setOpcionRespuesta(respuesta);
            pregunta.setPonderaciones(ponderaciones);
        } else if (ponderaciones != null) {
            pregunta.setPonderaciones(ponderaciones);
        }
return repository.save(pregunta);
    }

//-----------------------------------------------------------------------

    public List<Pregunta> obtenerTodos() {
        return repository.findAll();
    }

    /**
     * Eliminar logicamente una pregunta
     */

    public void eliminar(Pregunta pregunta) {
        pregunta.setEliminacion(true);
}
        //-----------------------------------------------------------------------------------
        public Pregunta obtenerPorId(Long id) {
        return repository.findByIdOrThrow(id,"la pregunta");}
    public void existePorId(Long id) {repository.existsByIdOrThrow(id,"la pregunta");}

    /**
     * Validar si alguna pregunta esta utilizando una opcion de respuesta x
     * {@link OpcionRespuesta}
     */
    public boolean existeOpcionRespuestaEnuso(Long id) {
        return repository.existsByOpcionRespuesta_Id(id);
    }
}

