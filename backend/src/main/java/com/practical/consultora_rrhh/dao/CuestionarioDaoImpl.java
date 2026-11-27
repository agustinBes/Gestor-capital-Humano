package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.dto.request.candidatoDtoAuth;
import com.practical.consultora_rrhh.interfaces.dao.IcuestionarioDao;
import com.practical.consultora_rrhh.interfaces.repositorio.cuestionarioRepository;
import com.practical.consultora_rrhh.modelo.clases.Cuestionario;
import com.practical.consultora_rrhh.modelo.enums.tipoDocumento;
import com.practical.consultora_rrhh.modelo.enums.tipoEstado;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class CuestionarioDaoImpl implements IcuestionarioDao {
    private final cuestionarioRepository repository;

    public CuestionarioDaoImpl(cuestionarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cuestionario> obtenerCompletadosPorPuesto(Long idPuesto) {
        return repository.findByPuesto_IdAndEstado(idPuesto,tipoEstado.COMPLETADO);
    }
   @Override
    @Transactional
    public List<Cuestionario> guardarTodos(List<Cuestionario> cuestionarios) {return repository.saveAll(cuestionarios);}
    @Override
    @Transactional
    public Cuestionario guardar(Cuestionario cuestionario) {return repository.save(cuestionario);   }


//Considero activo un cuestionario que este en proceso tambien al menos durante la obtencion inicial
    @Override
    public List<Cuestionario> cuestionariosActivos(List<Long> identificadores) {
        List<tipoEstado> estados=new ArrayList<>();
        estados.add(tipoEstado.ACTIVO);
        estados.add(tipoEstado.EN_PROCESO);
        return repository.findAllByCandidato_IdInAndEstadoIn(identificadores,estados);
    }

    @Override
    public Optional<Cuestionario> obtenerPorClave(String clave) {
        return  repository.findByClave(clave);
    }

    @Override
    public Optional<Cuestionario> obtenerPorClave(candidatoDtoAuth dto) {
        return repository.findByClaveAndCandidato_TipoAndCandidato_Documento(dto.clave(), tipoDocumento.desdeString(dto.tipoDocumento()),dto.documento());
    }

    @Override
    public List<String> obtenerClaves() { return  obtenerTodos().stream().map(Cuestionario::getClave).toList();}
//--------------------------------------------------------------------------------------

    @Override
    public List<Cuestionario> obtenerTodos() {        return repository.findAll();    }

    @Override
    public void eliminar(Cuestionario cuestionario) {repository.delete(cuestionario);    }

    @Override
    public Optional<Cuestionario> obtenerPorId(Long id) {return repository.findById(id);}
    @Override
    public boolean existePorId(Long aLong) {return repository.existsById(aLong);}

    @Override
    public List<Cuestionario> buscarPorPuesto(List<Long> puestos) {
        return  repository.findAllByPuesto_IdIn(puestos);
    }

    @Override
    public List<Cuestionario> buscarXPuesto(Long id) {
        return  repository.findByPuesto_Id(id);
    }

    @Override
    public List<Cuestionario> buscarxIds(List<Long> cuestionarios) {
        return  repository.findAllById(cuestionarios);
    }
}
