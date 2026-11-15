package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import com.practical.consultora_rrhh.interfaces.dao.IFactorDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceAuditoria;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.modelo.clases.Competencia;
import com.practical.consultora_rrhh.modelo.clases.Factor;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import com.practical.consultora_rrhh.services.mediator.FactorCompetenciaMediator;
import com.practical.consultora_rrhh.services.mediator.FactorPreguntaMediator;
import com.practical.consultora_rrhh.validaciones.FactorValidators;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
/**
 *  Servicio encargado de gestionar la lógica de negocio y las validaciones
 * pertinentes a un {@link Factor}.
 *
 * <p>Las validaciones comprenden aspectos relacionados con el formato,
 * el tipo de datos, las restricciones de unicidad de determinados campos
 * la relacion de un factor con una {@link Competencia}
 *
 *
 * <p>Se delegan las validaciones en clases Validators {@link FactorValidators} para mantener el
 * código organizado y facilitar su mantenimiento.</p>
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */

@Service
public class ServiceFactor implements IserviceFactor {
private final IFactorDao dao;
private final FactorCompetenciaMediator mediatorCompetencia;
private final FactorPreguntaMediator mediatorPreguntas;
private final FactorValidators validators;
private final IserviceAuditoria auditoria;
public ServiceFactor(IFactorDao dao, FactorCompetenciaMediator mediatorCompetencia, FactorPreguntaMediator mediatorPreguntas, FactorValidators validators, IserviceAuditoria auditoria) {
        this.dao = dao;
    this.mediatorCompetencia = mediatorCompetencia;
    this.mediatorPreguntas = mediatorPreguntas;
    this.validators = validators;
    this.auditoria = auditoria;
}
@PostConstruct
public void init(){
    mediatorPreguntas.registrarServicioA(this);
    mediatorCompetencia.registrarServicioA(this);
}
//--------------------------------------------------------------------------------------------
    /**
     * validar existencia de la competencia
     * validar disponibilidad de nombre
     * validar disponibilidad de codigo
     * validar la disponibilidad del Orden en relacion con la competencia
     * Crear el nuevo factor
     * Persistir la entidad
     */
    @Override
    public factorDto guardar(factorDtoCreate dto) {

        Competencia c= mediatorCompetencia.obtenerCompetencia(dto.id_competencia());
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
//--------------------------------------------------------------------------------------------
    /**
     * Validar existencia de la entidad mediante su id
     * detectar modificaciones en el nombre
     * detectar modificaciones en la descripcion
     * detectar cambio de competencia
     * Obtener nueva competencia y validar en el proceso
     * verificar el Nro de orden si se cambio o si cambio la competencia
     * Actualizar entidad
     */
    @Override
    public factorDto actualizar(Long id, factorDtoCreate dto) {
    Factor actual=this.obtenerEntidad(id);
  Map<String,String>  modificaciones= validators.validarModificaciones(actual,dto);
Competencia c=null;
Long idCompetencia=actual.getCompetencia().getId();
boolean orden=false;
        // 1. Si cambia la competencia
        if (!idCompetencia.equals(dto.id_competencia())) {
            c = mediatorCompetencia.obtenerCompetencia(dto.id_competencia());
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
//--------------------------------------------------------------------------------------------
    /**
     * Listar todos los factores
     */
    @Override
    public List<factorDto> obtenerTodos() {
        return dao.obtenerTodos()
                .stream()
                .map(factorDto::new)
                .toList();}
//--------------------------------------------------------------------------------------------
    /**
     * validar existencia de la entidad
     * eliminar logicamente las preguntas asociadas
     * generar nuevo registro de auditoria
     * eliminar logicamente el factor
     *
     * @return
     */
    @Override
    public String eliminar(Long id) {
    Factor factor=this.obtenerEntidad(id);
    mediatorPreguntas.eliminarPreguntas(id);
        LocalDateTime fecha = LocalDateTime.now();
        auditoriaDtoCreate aux=new auditoriaDtoCreate(null,factor.getNombre(), tipoAuditoria.FACTOR,fecha, "Juan pepe");
auditoria.crearAuditoria(aux);
String respuesta="Los datos del factor: "+factor.getNombre()+" han sido eliminados del sistema";
dao.eliminar(factor);
    return respuesta;}
//--------------------------------------------------------------------------------------------
    /**
     * Obtener Factor en un formato adecuado  para el controlador
     */
    public factorDto obtenerPorId(Long id) {
        Factor f=this.obtenerEntidad(id);
    return new factorDto(f);}
//--------------------------------------------------------------------------------------------
        /**
     * Obtener Entidad para otro servicio que lo requiera
     */
    public Factor obtenerEntidad(Long id) {
        return    dao.obtenerPorId(id).orElseThrow(() ->
                new EntityNotFoundException("No se encontró el factor con el id: "+id+" en el sistema"));}
//--------------------------------------------------------------------------------------------
    /**
     * Validar existencia de una entidad mediante su id
     */
     public void validarEntidad(Long id) {
if(!dao.existePorId(id)){
    throw new  EntityNotFoundException("No se encontro el factor con el id: "+id);}}
//--------------------------------------------------------------------------------------------
    public List<factorDto> buscar(factorFiltrosDto dto) {
    validators.validarCriterios(dto);
    return dao.buscar(dto.factor(), dto.competencia())
                .stream().map(factorDto::new)
                .toList();}
//--------------------------------------------------------------------------------------------

    @Override
    public boolean validarCompetenciaEnUso(Long idCompetencia) {
        return dao.existeCompetenciaEnUso(idCompetencia);
    }
}
