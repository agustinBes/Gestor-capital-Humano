package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
import com.practical.consultora_rrhh.excepciones.genericas.ColeccionNotFoundException;
import com.practical.consultora_rrhh.excepciones.genericas.EntidadEnUsoException;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import com.practical.consultora_rrhh.interfaces.dao.ICompetenciaDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceAuditoria;
import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import com.practical.consultora_rrhh.services.mediator.CompetenciaPuestoMediator;
import com.practical.consultora_rrhh.services.mediator.FactorCompetenciaMediator;
import com.practical.consultora_rrhh.validaciones.CompetenciaValidators;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ServiceCompetencia implements IserviceCompetencia {

    private final ICompetenciaDao dao;

    private final FactorCompetenciaMediator mediatorFactor;
    private final CompetenciaPuestoMediator mediatorPuesto;
    private final CompetenciaValidators validators;
    private final IserviceAuditoria auditoria;
    public ServiceCompetencia(ICompetenciaDao competenciaDao, FactorCompetenciaMediator mediatorFactor, CompetenciaPuestoMediator mediatorPuesto, CompetenciaValidators validators, IserviceAuditoria auditoria) {
        this.dao = competenciaDao;
        this.mediatorFactor = mediatorFactor;
        this.mediatorPuesto = mediatorPuesto;
        this.validators = validators;
        this.auditoria = auditoria;
    }
@PostConstruct
public void init(){
        mediatorFactor.registrarServicioB(this);
        mediatorPuesto.registrarServicioA(this);
}

    public competenciaDto guardar(competenciaDtoCreate dto) {
        Map<String,String> map = validators.validarCreacion(dto);

        Competencia competencia = Competencia.builder()
                .id(null)
                .nombre(map.get("nombre"))
                .codigo(map.get("codigo"))
                .descripcion(map.get("descripcion"))
                .tipoCompetencia(dto.tipo())
                .factores(new ArrayList<>())
                .eliminacion(false)
                .build();

        Competencia guardada=dao.guardar(competencia);
        return new competenciaDto(guardada);
    }

    public competenciaDto actualizar(Long id, competenciaDtoCreate dto) {
       Competencia actual=this.obtenerEntidad(id);
        Map<String,String> modificacion=validators.validarModificacion(dto,actual);
        boolean nombre,descripcion;
        if(modificacion!=null){
nombre= modificacion.containsKey("nombre");
descripcion=modificacion.containsKey("descripcion");
    dao.actualizar(actual,nombre? modificacion.get("nombre"):null,
            descripcion? modificacion.get("descripcion"):null);
        }
return   new competenciaDto(actual);
    }

    public List<competenciaDto> obtenerTodos() {

        return dao.obtenerTodos()
                .stream()
                .map(competenciaDto::new)
                .toList();
    }

    @Override
    public List<competenciaDto> buscar(String nombre) {
       if(nombre==null || nombre.isEmpty()){
           return  obtenerTodos();}


           String normalizar= TextoUtils.normalizar(nombre);
List<competenciaDto> result= dao.buscar(normalizar)
        .stream()
        .map(competenciaDto::new)
        .toList();

if(result.isEmpty()){
throw  new ColeccionNotFoundException();}
return result;}


    public String eliminar(Long id, Long idConsultor) {
        Competencia  competencia=this.obtenerEntidad(id);

        if(competencia.isEliminacion()){
            String r=auditoria.buscarAuditor(id,competencia.getNombre(),tipoAuditoria.COMPETENCIA);
   return "La competencia con el nombre: "+ competencia.getNombre()+ "ya fue eliminada por el consultor: "+ r;
        }
        if(mediatorFactor.competenciaEnUso(id) ||
mediatorPuesto.CompetenciaEnUso(id)) {
           throw new EntidadEnUsoException("La competencia no puede ser eliminada dado que otros recursos del sistema la estan usando");
       }


       auditoriaDtoCreate aux=new auditoriaDtoCreate(null,id,competencia.getNombre(), tipoAuditoria.COMPETENCIA, LocalDateTime.now(),idConsultor);
auditoria.crearAuditoria(aux);
String respuesta="Los datos de la Competencia: "+competencia.getNombre()+" han sido eliminados del sistema";
    dao.eliminar(competencia);
    return respuesta;}

//-------------------------------------------------------------------

    public competenciaDto obtenerPorId(Long id) {
        Competencia entidad=this.obtenerEntidad(id);
        return new  competenciaDto(entidad);}

//-------------------------------------------------------------------
    public Competencia obtenerEntidad(Long id){
     return    dao.obtenerPorId(id).orElseThrow(() ->
             new EntityNotFoundException(Competencia.class, id.toString()));
}
//-------------------------------------------------------------------

    public void validarEntidad(Long id) {
        boolean aux = dao.existePorId(id);
        if (!aux) {
            throw
                    new EntityNotFoundException(Competencia.class, id.toString());}}

    @Override
    public List<Competencia> validarCompetencias(List<Long> idCompetencias) {
        List<Competencia> list=new ArrayList<>();
        for(Long id:idCompetencias){
            list.add(obtenerEntidad(id));
        }
        return list;
    }

}
