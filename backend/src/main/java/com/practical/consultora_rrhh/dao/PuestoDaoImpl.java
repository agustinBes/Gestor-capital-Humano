package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.interfaces.dao.IPuestoDao;
import com.practical.consultora_rrhh.interfaces.repositorio.puestoRepository;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository
public class PuestoDaoImpl implements IPuestoDao {
private final puestoRepository repositoriy;
public PuestoDaoImpl(puestoRepository repositoriy) {
    this.repositoriy = repositoriy;
}
@Override
    public Puesto guardar(Puesto puesto) {
        return  repositoriy.save(puesto);}

    @Override
   @Transactional
    public Puesto actualizar(Puesto actual, Map<String, String> modificacion) {


        if(modificacion.containsKey("nombre")) {
            actual.setNombre(modificacion.get("nombre"));}
        if(modificacion.containsKey("descripcion")) {
            actual.setDescripcion(modificacion.get("descripcion"));
        }
        if(modificacion.containsKey("empresa")) {
            actual.setEmpresa(modificacion.get("empresa"));
        }

    return actual;
    }

    @Override
    public List<Puesto> obtenerTodos() {
        return repositoriy.findAll();}

    @Override
    @Transactional
    public void eliminar(Puesto puesto) {puesto.setEliminacion(true);    }

    @Override
    public Optional<Puesto> obtenerPorId(Long id) {
        return repositoriy.findById(id);}

    @Override
    public boolean existePorId(Long id) {
        return repositoriy.existsById(id);   }



    @Override
    public boolean existePorNombreInsensible(String nombre) {
        return               repositoriy.existsByNombreIgnoreCase(nombre);}

    @Override
    public boolean existePorCodigoInsensible(String codigo) {
        return repositoriy.existsByCodigoIgnoreCase(codigo);}
}
