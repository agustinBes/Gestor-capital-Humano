package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.PreguntaFiltrosDto;
import com.practical.consultora_rrhh.interfaces.dao.IPreguntaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.preguntaRepository;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.modelo.clases.Ponderacion;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PreguntaDaoImpl  implements IPreguntaDao {

    private final preguntaRepository repository;

    public PreguntaDaoImpl(preguntaRepository repository) {

        this.repository = repository;
    }


    public List<Pregunta> buscar(PreguntaFiltrosDto filtros) {
        return List.of();
    }

    //-----------------------------------------------------------------------------------------------
    public boolean existeNombrePorFactorInsensible(String nombre, Long idFactor, Long idCompetencia) {
        return repository.existeNombrePorFactorCompetenciaInsensible(nombre, idFactor, idCompetencia);
    }

    public boolean existePreguntaPorFactorInsensible(String nombre, Long idFactor, Long competencia) {
        return repository.existePreguntaPorFactorCompetenciaInsensible(nombre, idFactor, competencia);
    }


    public Pregunta guardar(Pregunta pregunta) {
        return repository.save(pregunta);
    }

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

    public void eliminar(Pregunta pregunta) {
repository.delete(pregunta);}
        //-----------------------------------------------------------------------------------
        public Pregunta obtenerPorId(Long id) {
        return repository.findByIdOrThrow(id,"la pregunta");}
    public void existePorId(Long id) {repository.existsByIdOrThrow(id,"la pregunta");}

    public boolean existeOpcionRespuestaEnuso(Long id) {
        return repository.existsByOpcionRespuesta_Id(id);
    }
}

