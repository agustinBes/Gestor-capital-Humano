package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.IpuestoCompetenciaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.puestoCompetenciaRepository;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import com.practical.consultora_rrhh.modelo.id.PuestoCompetenciaId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class PuestoCompetenciaDaoImpl implements IpuestoCompetenciaDao {
    private final puestoCompetenciaRepository repository;

    public PuestoCompetenciaDaoImpl(puestoCompetenciaRepository repository) {
        this.repository = repository;
    }

    public PuestoCompetencia guardar(PuestoCompetencia x){
        return repository.save(x);
    }

    @Override
   @Transactional
    public PuestoCompetencia actualizarPonderacion(PuestoCompetencia pc, short ponderacion) {
        pc.setPonderacion(ponderacion);
        return pc;
    }

    @Override
    @Transactional
    public List<PuestoCompetencia> actualizarPuestoCompetencia(Long idPuesto,  List<PuestoCompetencia> caracteristicas) {

        Map<Long, PuestoCompetencia> guardadas = obtenerPorPuesto(idPuesto).stream()
                .collect(Collectors.toMap(pc -> pc.getCompetencia().getId(),Function.identity()));

        for (PuestoCompetencia nueva : caracteristicas) {
            Long idCompetencia = nueva.getCompetencia().getId();
            PuestoCompetencia actual = guardadas.get(idCompetencia);
//Vemos si en la lista de guardadas ya existia la competencia o guardamos una nueva
            if (actual == null) {guardar(nueva);}

            else {
            //revisamos si requereimos actualizar la ponderacion
                if (actual.getPonderacion()!=nueva.getPonderacion()) {actualizarPonderacion(actual,nueva.getPonderacion());}
                //removemos la competencia ya resuelta
                guardadas.remove(idCompetencia);}
        }
        //eliminamos las asociaciones entre puesto y competencia que ya no existen
        for (PuestoCompetencia eliminada : guardadas.values()) {
            eliminar(eliminada);
        }

        return obtenerPorPuesto(idPuesto);
    }


    @Override
    public boolean existePuestoCompetencia(Long idPuesto, Long idCompetencia) {
        return  repository.existsByCompetencia_IdAndPuesto_Id(idCompetencia,idPuesto);
    }

    public void eliminar(PuestoCompetencia x){
repository.delete(x);}


    @Override
    public List<PuestoCompetencia> obtenerPorCompetencia(Long id) {
        return repository.findByCompetencia_Id(id);
    }

    @Override
    public List<PuestoCompetencia> obtenerPorPuesto(Long id) {
        return repository.findByPuesto_Id(id);
    }

    @Override
    public List<PuestoCompetencia> obtenerPorPuestos(List<Long> coleccion) {
        return repository.findByPuesto_IdIn(coleccion);
    }
    @Override
    @Transactional
    public void eliminarPorPuesto(Long idPuesto) {
        List<PuestoCompetencia> lista=obtenerPorPuesto(idPuesto);
        lista.forEach(this::eliminar);
    }


    @Override
    public List<PuestoCompetencia> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public Optional<PuestoCompetencia> obtenerPorId(PuestoCompetenciaId puestoCompetenciaId) {
        return repository.findById(puestoCompetenciaId);
    }

    @Override
    public boolean existePorId(PuestoCompetenciaId puestoCompetenciaId) {
        return repository.existsById(puestoCompetenciaId);
    }


    @Override
    public boolean existeCompetenciaEnUso(Long id) {
        return repository.existsByCompetencia_Id(id);
    }
}
