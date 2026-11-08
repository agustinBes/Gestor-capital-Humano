package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.PreguntaFiltrosDto;
import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.request.preguntaDtoCreate;
import com.practical.consultora_rrhh.dto.response.preguntaDto;
import com.practical.consultora_rrhh.interfaces.dao.IPreguntaDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.interfaces.services.IserviceOpcionRespuesta;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.*;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import com.practical.consultora_rrhh.validaciones.PreguntaValidators;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Servicio encargado de gestionar la lógica de negocio y las validaciones
 * pertinentes a una {@link Pregunta}.
 *
 * <p>Las validaciones comprenden aspectos relacionados con el formato,
 * el tipo de datos, las restricciones de unicidad de determinados campos
 * y la integridad referencial entre un {@link Factor} y la
 *    {@link Competencia} asociada.</p>
 *
 *
 * <p>Se delegan las validaciones en clases Validators {@link PreguntaValidators} para mantener el
 * código organizado y facilitar su mantenimiento.</p>
 *
 * @author  Besigniano Agustin
 * @Since 1.0
 */


@Service
public class ServicePregunta implements IservicePregunta {
    private final PreguntaValidators validators;
    private final IPreguntaDao dao;
    private final IserviceFactor serviceFactor;
    private final IserviceOpcionRespuesta serviceOpcionRespuesta;
    private final ServiceAuditoria  auditoria;
    public ServicePregunta(PreguntaValidators validators, IPreguntaDao dao, IserviceFactor serviceFactor, IserviceOpcionRespuesta serviceOpcionRespuesta, ServiceAuditoria auditoria) {
        this.validators = validators;
        this.dao = dao;
 this.serviceFactor = serviceFactor;
    this.serviceOpcionRespuesta = serviceOpcionRespuesta;
        this.auditoria = auditoria;
    }
//---------------------------------------------------------------------------------------
   /**
     * validar disponibilidad del nombre
     * validar disponibilad de la pregunta
     * validar que la suma de las ponderaciones de un total de 10
     * validar existencia del factor(y obtenerlo)
     * validar existencia de la opcion de respuesta y obtenerlo junto con las opciones
     * validar integridad referencial entre el factor y la competencia
     * construir {@link Ponderacion} para persistir en la bdd
     * crear la nueva pregunta con la informacion validada y id null
     * Persistir la pregunta  y retornarla
     */
    public preguntaDto guardar(preguntaDtoCreate dto) {
        Map<String,String> normalizado=new HashMap<>();

                  normalizado= validators.validacionInicial(dto);

    Factor f=serviceFactor.obtenerEntidad(dto.factor().id());
validators.validarCompetencia(f,dto.factor().competencia().id());

    OpcionRespuesta opcionRespuesta=serviceOpcionRespuesta.obtenerEntidad(dto.id_opcionRespuesta());

List<Ponderacion> ponderaciones=validators.construirPonderaciones(dto.ponderaciones(),opcionRespuesta.getOpciones(),dto.id_opcionRespuesta());
        Pregunta nueva=Pregunta.crear(normalizado.get("titulo"), normalizado.get("pregunta"), dto,opcionRespuesta,f,ponderaciones);

        Pregunta guardada=dao.guardar(nueva);
     return new preguntaDto(guardada);
    }
//---------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------

    public List<preguntaDto> buscar(PreguntaFiltrosDto dto) {
        return List.of();
    }
//---------------------------------------------------------------------------------------

    public preguntaDto actualizar(Long id, preguntaDtoCreate dto) {
    Pregunta actual=dao.obtenerPorId(id);

    boolean aux=validators.validarModificacion(actual, dto);

if(!aux){    return new preguntaDto(actual);}


    }
//---------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------

    public List<preguntaDto> obtenerTodos() {
        return dao.obtenerTodos().stream()
                        .map(preguntaDto::new)
                                .toList();
    }
//---------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------
    public void eliminar(Long id) {
        Pregunta eliminar=dao.obtenerPorId(id);
        LocalDateTime fecha=LocalDateTime.now();
auditoriaDtoCreate aux=new auditoriaDtoCreate(null,eliminar.getNombre(), tipoAuditoria.PREGUNTA,fecha,"Juan pepe");
auditoria.crearAuditoria(aux);
                dao.eliminar(eliminar);}
//---------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------

    public preguntaDto obtenerPorId(Long id) {return new preguntaDto(dao.obtenerPorId(id));}
    public Pregunta obtenerEntidad(Long id) { return dao.obtenerPorId(id);    }
    public void validarEntidad(Long id) { dao.existePorId(id);    }

    public boolean existeOpcionRespuestaEnUso(Long id) {
        return  dao.existeOpcionRespuesta(id);
    }
}
