package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import com.practical.consultora_rrhh.services.mediator.FactorCompetenciaMediator;
import com.practical.consultora_rrhh.validaciones.FactorValidators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ServiceFactor implements IserviceFactor {
private final IFactorDao dao;
private final FactorCompetenciaMediator mediator;
private final FactorValidators validators;
public ServiceFactor(IFactorDao dao, FactorCompetenciaMediator mediator, FactorValidators validators) {
        this.dao = dao;
    this.mediator = mediator;
    this.validators = validators;
}

    @Override
    public factorDto guardar(factorDtoCreate dto) {

        Competencia c=mediator.obtenerCompetencia(dto.id_competencia());
        String nombre=TextoUtils.normalizar(dto.nombre());
        String codigo=TextoUtils.normalizar(dto.codigo());
        validators.existePorNombreInsensible(nombre);
validators.existeCodigoInsensible(codigo);

validators.existeOrdenPorCompetencia(dto.nroOrden(), dto.id_competencia());

String descripcion= TextoUtils.normalizar(dto.descripcion());

Factor nuevo=Factor.builder()
        .eliminacion(false)
        .id(null)
        .orden(dto.nroOrden())
        .nombre(nombre)
        .codigo(codigo)
        .competencia(c)
        .descripcion(descripcion)
        .build();
Factor guardado=dao.guardar(nuevo);
return  new factorDto(guardado);
    }

    @Override
    public factorDto actualizar(Long id, factorDtoCreate dto) {
    Factor actual=this.obtenerEntidad(id);
  Map<String,String>  modificaciones= validators.validarModificaciones(actual,dto);
Competencia c=null;
Long idCompetencia=actual.getCompetencia().getId();
boolean orden=false;
        // 1. Si cambia la competencia
        if (!idCompetencia.equals(dto.id_competencia())) {
            c = mediator.obtenerCompetencia(dto.id_competencia());
            idCompetencia = dto.id_competencia(); // Actualizamos el ID para la validación final
            orden = true; // Al cambiar de competencia, el número de orden se tiene que setear sí o sí
        }
// 2. Si es la misma competencia pero cambió el número de orden
        else if (actual.getOrden() != dto.nroOrden()) {
            orden = true;
        }

        if (orden) {
            validators.existeOrdenPorCompetencia(dto.nroOrden(), idCompetencia);
        }
if(modificaciones!=null || c!=null || orden){
    dao.actualizar(actual,modificaciones,c,
            orden? dto.nroOrden() : null);
}
return new factorDto(actual);
 }

    @Override
    public List<factorDto> obtenerTodos() {
        return List.of();
    }

    @Override
    public void eliminar(Long aLong) {

    }

    public factorDto obtenerPorId(Long id) {
        Factor f=this.obtenerEntidad(id);
    return new factorDto(f);}

    public boolean validarExistencia(Long id) {
        return false;
    }


    public Factor obtenerEntidad(Long id) {

        return    dao.obtenerPorId(id).orElseThrow(() ->
                new EntityNotFoundException("No se encontró la entidad con el id: "+id+" en el sistema"));
    }

    public void validarConsistencia(Long id, Long idCompetencia) {
         dao.validarCompetencia(id,idCompetencia);
    }

    public void validarEntidad(Long aLong) {

    }

    public List<factorDto> buscar(factorFiltrosDto dto) {
    validators.validarCriterios(dto);

    return dao.buscar(dto.factor(), dto.competencia())
                .stream().map(factorDto::new)
                .toList();


}

}
