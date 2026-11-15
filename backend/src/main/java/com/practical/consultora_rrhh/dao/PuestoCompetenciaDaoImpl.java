package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.IpuestoCompetenciaDao;
import com.practical.consultora_rrhh.interfaces.repositorio.puestoCompetenciaRepository;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import com.practical.consultora_rrhh.modelo.id.PuestoCompetenciaId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class PuestoCompetenciaDaoImpl implements IpuestoCompetenciaDao {
    private final puestoCompetenciaRepository repository;

    public PuestoCompetenciaDaoImpl(puestoCompetenciaRepository repository) {
        this.repository = repository;
    }

    public PuestoCompetencia guardar(PuestoCompetencia x){
        return repository.save(x);
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
