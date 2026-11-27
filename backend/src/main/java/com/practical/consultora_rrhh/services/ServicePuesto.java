package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.request.puestoCompetenciaDtoCreate;
import com.practical.consultora_rrhh.dto.request.puestoDtoCreate;
import com.practical.consultora_rrhh.dto.response.puestoDto;
import com.practical.consultora_rrhh.excepciones.genericas.ColeccionNotFoundException;
import com.practical.consultora_rrhh.excepciones.genericas.EntidadEnUsoException;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import com.practical.consultora_rrhh.excepciones.genericas.FiltroVacioException;
import com.practical.consultora_rrhh.interfaces.dao.IPuestoDao;
import com.practical.consultora_rrhh.interfaces.dao.IpuestoCompetenciaDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceAuditoria;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import com.practical.consultora_rrhh.modelo.clases.Puesto;
import com.practical.consultora_rrhh.modelo.clases.PuestoCompetencia;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import com.practical.consultora_rrhh.services.mediator.CompetenciaPuestoMediator;
import com.practical.consultora_rrhh.services.mediator.OrdenMeritoPuestoMediator;
import com.practical.consultora_rrhh.services.mediator.PuestoCuestionarioMediator;
import com.practical.consultora_rrhh.validaciones.PuestoValidators;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServicePuesto implements IservicePuesto {
    private final IPuestoDao dao;
    private final IpuestoCompetenciaDao puestoCompetenciaDao;
    private final CompetenciaPuestoMediator mediatorCompetencia;
    private final PuestoValidators validators;
    private final IserviceAuditoria auditoria;
    private final PuestoCuestionarioMediator cuestionarioMediator;
private final OrdenMeritoPuestoMediator ordenMeritoMediator;
    public ServicePuesto(IPuestoDao dao, IpuestoCompetenciaDao puestoCompetenciaDao, CompetenciaPuestoMediator mediatorCompetencia, PuestoValidators validators, IserviceAuditoria auditoria, PuestoCuestionarioMediator cuestionarioMediator, OrdenMeritoPuestoMediator ordenMeritoMediator) {
        this.dao = dao;
        this.puestoCompetenciaDao = puestoCompetenciaDao;
        this.mediatorCompetencia = mediatorCompetencia;
        this.validators = validators;
        this.auditoria = auditoria;
        this.cuestionarioMediator = cuestionarioMediator;
        this.ordenMeritoMediator = ordenMeritoMediator;
    }

    @PostConstruct
    public void init() {

        mediatorCompetencia.registrarServicioB(this);
       cuestionarioMediator.registrarServicioA(this);
   ordenMeritoMediator.registrarServicioB(this);
    }



    @Override
    public puestoDto guardar(puestoDtoCreate dto) {
        Map<String, String> aux = validators.validarCreacionModificacion(null, dto);

        List<Competencia> competencias = mediatorCompetencia.validarCompetencias(dto.caracteristicas().keySet().stream().toList());

        Puesto actual = Puesto.builder()
                .codigo(aux.get("codigo"))
                .descripcion(aux.get("descripcion"))
                .id(null)
                .empresa(aux.get("empresa"))
                .nombre(aux.get("nombre"))
                .eliminacion(false)
                .build();
        Puesto guardado = dao.guardar(actual);

        List<puestoCompetenciaDtoCreate> caracteristicas = new ArrayList<>();

        competencias.forEach(competencia -> {
            puestoCompetenciaDtoCreate nuevo = new puestoCompetenciaDtoCreate(guardado, competencia, dto.caracteristicas().get(competencia.getId()));
            caracteristicas.add(nuevo);
        });

        return new puestoDto(guardado, guardarCaracteristicas(caracteristicas));
    }


    @Override
    public puestoDto actualizar(Long id, puestoDtoCreate dto) {
        Puesto actual = obtenerEntidad(id),actualizado=null;

        Map<String, String> modificacion = validators.validarCreacionModificacion(actual, dto);
       List<PuestoCompetencia> caracteristicas=actualizarCaracteristicas(actual, dto.caracteristicas());
if(!modificacion.isEmpty()){actualizado=dao.actualizar(actual,modificacion);}

return new puestoDto(actualizado ==null? actual:actualizado,caracteristicas);
    }

    @Override
    public List<puestoDto> obtenerTodos() {
        return dao.obtenerTodos()
                .stream()
                .map(puestoDto::new)
                .toList();
    }

    @Override
    public boolean existeCompetenciaEnUso(Long id) {

        return puestoCompetenciaDao.existeCompetenciaEnUso(id);
    }

    public Puesto obtenerEntidad(Long id) {
        return dao.obtenerPorId(id).orElseThrow(() ->
 new EntityNotFoundException(Puesto.class, id.toString()));
    }


    @Override
    public void validarEntidad(Long id) {
        if (!dao.existePorId(id)) {
         throw    new EntityNotFoundException(Puesto.class,id.toString());
        }
    }



    @Override
    public String eliminar(Long id, Long idConsultor) {
        Puesto puesto = obtenerEntidad(id);
if(puesto.isEliminacion()){
    String r=auditoria.buscarAuditor(id, puesto.getNombre(),tipoAuditoria.PUESTO );
    return "El Puesto "+ puesto.getNombre()+" ya fue eliminado del sistema por el consultor "+r;
}
boolean Enuso=cuestionarioMediator.existePuestoEnUso(id);
if(Enuso){
    throw new EntidadEnUsoException("El puesto  esta siendo usado por otros recursos en el sistema");
}
        dao.eliminar(puesto);

        String respuesta = "Los datos del puesto: " + puesto.getNombre() + " han sido eliminados del sistema";
//si deseamos eliminar las caracteristicas del puesto se debe preservar en alguna entidad
// las caracteristicas del mismo ya que aun son datos que sirven para generar reportes
        //        puestoCompetenciaDao.eliminarPorPuesto(id);
        auditoriaDtoCreate nueva = new auditoriaDtoCreate(null,id, puesto.getNombre(), tipoAuditoria.PUESTO, LocalDateTime.now(), idConsultor);
        auditoria.crearAuditoria(nueva);


        return respuesta;
    }

    @Override
    public puestoDto obtenerPorId(Long id) {
        Puesto p = obtenerEntidad(id);
        List<PuestoCompetencia> c=puestoCompetenciaDao.obtenerPorPuesto(id);
        return c.isEmpty()? new puestoDto(p):new puestoDto(p,c);
    }

    @Override
    public List<puestoDto> buscar(puestoFiltrosDto dto) {
        if(validators.validarFiltros(dto)) {

            List<Puesto> puestos=dao.buscar(dto);

            List<Long> coleccion=puestos.stream().map(Puesto::getId).toList();
            List<PuestoCompetencia> competencias=puestoCompetenciaDao.obtenerPorPuestos(coleccion);


            List<puestoDto> resultados=new ArrayList<>();
            for (Puesto puesto : puestos) {
                List<PuestoCompetencia> lista = competencias.stream()
                        .filter(pc -> pc.getPuesto().getId().equals(puesto.getId()))
                        .toList();

                resultados.add(new puestoDto(puesto,lista));
            }
return resultados;

        }

        throw new FiltroVacioException();

    }



    @Override
    public Map<Puesto,List<PuestoCompetencia>> buscarParaOrdenMerito(puestoFiltrosDto dto) {
        if(validators.validarFiltros(dto)) {

            List<Puesto> puestos=dao.buscar(dto);

            if(puestos.isEmpty()){
                throw new ColeccionNotFoundException("No se encontraron puestos");
            }
            List<Long> coleccion=puestos.stream().map(Puesto::getId).toList();
            List<PuestoCompetencia> competencias=puestoCompetenciaDao.obtenerPorPuestos(coleccion);
Map<Puesto,List<PuestoCompetencia>> resultados=new HashMap<>();
for (Puesto puesto : puestos) {
    List<PuestoCompetencia> filas=competencias.stream().toList()
            .stream().filter(o->o.getPuesto().equals(puesto))
            .toList();

    resultados.put(puesto,filas);}

return  resultados;
        }

        throw new FiltroVacioException();

    }


    //-------------------------------------------------------------------------------------------------------
    @Override
    public List<PuestoCompetencia> guardarCaracteristicas(List<puestoCompetenciaDtoCreate> caracteristicas) {
        List<PuestoCompetencia> lista = caracteristicas.stream()
                .map(PuestoCompetencia::new)
                .toList();
        lista.forEach(puestoCompetenciaDao::guardar);
        return lista;
    }

        public List<PuestoCompetencia> actualizarCaracteristicas(Puesto p, Map<Long, Short> caracteristicas) {
            boolean enUso=cuestionarioMediator.existePuestoEnUso(p.getId());
            if(enUso){
                throw new EntidadEnUsoException("El cuestionario esta siendo usado por otros recursos en el sistema");
            }

List<PuestoCompetencia> respuesta = new ArrayList<>();
        List<Competencia> competencias = mediatorCompetencia.validarCompetencias(caracteristicas.keySet().stream().toList());
            for (Competencia c : competencias) {
                respuesta.add(PuestoCompetencia.builder()
                        .puesto(p)
                        .competencia(c)
                        .ponderacion(caracteristicas.get(c.getId()))
                        .build());
            }
        return puestoCompetenciaDao.actualizarPuestoCompetencia(p.getId(),respuesta);
    }
    public List<PuestoCompetencia> obtenerPorPuesto(Long id) {
 List<PuestoCompetencia> obtenidos=  puestoCompetenciaDao.obtenerPorPuesto(id);

 if(obtenidos.isEmpty()){
     throw new EntityNotFoundException(Puesto.class,id.toString());
 }
    //    System.out.println(obtenidos.stream().map(o->o.getCompetencia().getNombre()).collect(Collectors.joining()));
    return obtenidos;
    }

    }

