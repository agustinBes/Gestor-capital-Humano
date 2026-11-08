package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.PreguntaFiltrosDto;
import com.practical.consultora_rrhh.interfaces.dao.IPreguntaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.preguntaRepository;
import com.practical.consultora_rrhh.modelo.clases.Pregunta;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public boolean existeNombrePorFactorInsensible(String nombre,Long idFactor,Long idCompetencia) {
return  repository.existeNombrePorFactorCompetenciaInsensible(nombre,idFactor,idCompetencia);
    }
public boolean existePreguntaPorFactorInsensible(String nombre, Long idFactor, Long competencia) {
        return repository.existePreguntaPorFactorCompetenciaInsensible(nombre,idFactor,competencia);}


    public Pregunta guardar(Pregunta pregunta) {
        return repository.save(pregunta);
    }

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

