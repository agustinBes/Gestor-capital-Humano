package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.request.puestoDtoCreate;
import com.practical.consultora_rrhh.dto.response.puestoDto;
import com.practical.consultora_rrhh.interfaces.dao.IPuestoDao;
import com.practical.consultora_rrhh.interfaces.dao.IpuestoCompetenciaDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceAuditoria;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicePuesto implements IservicePuesto {
    private final IPuestoDao dao;
    private final IpuestoCompetenciaDao puestoCompetenciaDao;
    private final CompetenciaPuestoMediator mediatorCompetencia;
    private final IserviceAuditoria auditoria;
    public ServicePuesto(IPuestoDao dao, IpuestoCompetenciaDao puestoCompetenciaDao, CompetenciaPuestoMediator mediatorCompetencia, IserviceAuditoria auditoria) {
        this.dao = dao;
        this.puestoCompetenciaDao = puestoCompetenciaDao;
        this.mediatorCompetencia = mediatorCompetencia;
        this.auditoria = auditoria;
    }
@PostConstruct
public void init(){
        mediatorCompetencia.registrarServicioB(this);
}

    @Override
    public boolean existeCompetenciaEnUso(Long id) {
        return false;
    }

    @Override
    public Puesto obtenerEntidad(Long aLong) {
        return null;
    }

    @Override
    public void validarEntidad(Long aLong) {

    }

    @Override
    public puestoDto guardar(puestoDtoCreate dto) {
        return null;
    }

    @Override
    public puestoDto actualizar(Long aLong, puestoDtoCreate dto) {
        return null;
    }

    @Override
    public List<puestoDto> obtenerTodos() {
        return List.of();
    }

    @Override
    public String eliminar(Long id) {
        Puesto puesto = obtenerEntidad(id);
        //logica para ver si esta en uso

        String respuesta = "Los datos del puesto: "+puesto.getDescripcion()+" han sido eliminados del sistema";
        puestoCompetenciaDao.eliminarPorPuesto(id);
        auditoriaDtoCreate nueva=new auditoriaDtoCreate(null,puesto.getNombre(), tipoAuditoria.PUESTO, LocalDateTime.now(),"juan pepe");
        auditoria.crearAuditoria(nueva);


        return respuesta;
    }

    @Override
    public puestoDto obtenerPorId(Long aLong) {
        return null;
    }
}


