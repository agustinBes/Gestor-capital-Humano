package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.dao.specifications.PuestoSpecifications;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.interfaces.dao.IPuestoDao;
import com.practical.consultora_rrhh.interfaces.repositorio.puestoRepository;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import com.practical.consultora_rrhh.dao.specifications.TextoSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public class PuestoDaoImpl implements IPuestoDao {
    private final puestoRepository repository;

    public PuestoDaoImpl(puestoRepository repositoriy) {
        this.repository = repositoriy;
    }

    @Override
    public Puesto guardar(Puesto puesto) {
        return repository.save(puesto);
    }

    @Override
    @Transactional
    public Puesto actualizar(Puesto actual, Map<String, String> modificacion) {


        if (modificacion.containsKey("nombre")) {
            actual.setNombre(modificacion.get("nombre"));
        }
        if (modificacion.containsKey("descripcion")) {
            actual.setDescripcion(modificacion.get("descripcion"));
        }
        if (modificacion.containsKey("empresa")) {
            actual.setEmpresa(modificacion.get("empresa"));
        }

        return actual;
    }

    @Override
    public List<Puesto> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void eliminar(Puesto puesto) {
        puesto.setEliminacion(true);
    }

    @Override
    public void reactivar(Puesto puesto) {
        puesto.setEliminacion(false);
    }

    @Override
    public Optional<Puesto> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }


    @Override
    public boolean existePorNombreInsensible(String nombre) {

        Specification<Puesto> filtro = TextoSpecifications.coincidencia("nombre", nombre, false, true);
        return repository.exists(filtro);
    }

    @Override
    public boolean existePorCodigoInsensible(String codigo) {
        Specification<Puesto> filtro = TextoSpecifications.coincidencia("codigo", codigo, false, true);
        return repository.exists(filtro);
    }

    @Override
    public List<Puesto> buscar(puestoFiltrosDto dto) {
        Specification<Puesto> filtros= PuestoSpecifications.buscar(dto);
        return repository.findAll(filtros);

    }
}
