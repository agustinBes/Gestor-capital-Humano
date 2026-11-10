package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dao.PreguntaDaoImpl;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.request.preguntaDtoCreate;
import com.practical.consultora_rrhh.dto.response.preguntaDto;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import com.practical.consultora_rrhh.interfaces.dao.IPreguntaDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.*;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import com.practical.consultora_rrhh.services.mediator.PreguntaOpcionRMediator;
import com.practical.consultora_rrhh.validaciones.internas.PonderacionValidators;
import com.practical.consultora_rrhh.validaciones.PreguntaValidators;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
 * @since 1.0
 */

@Service
public class ServicePregunta implements IservicePregunta {
    private final PreguntaValidators validators;
    private final PonderacionValidators ponderacionValidators;
    private final IPreguntaDao dao;
    private final IserviceFactor serviceFactor;
    private final PreguntaOpcionRMediator mediador;
    private final ServiceAuditoria auditoria;

    public ServicePregunta(PreguntaValidators validators, PonderacionValidators ponderacionValidators, IPreguntaDao dao, IserviceFactor serviceFactor, PreguntaOpcionRMediator mediador, ServiceAuditoria auditoria) {
        this.validators = validators;
        this.ponderacionValidators = ponderacionValidators;
        this.dao = dao;
        this.serviceFactor = serviceFactor;
        this.mediador = mediador;
        this.auditoria = auditoria;
    }
    @PostConstruct
    public void init() {
        mediador.registrarServicioA(this);
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
        Factor f = serviceFactor.obtenerEntidad(dto.idFactor());

        Map<String, String> normalizado = validators.validacionInicial(dto,f);


        OpcionRespuesta opcionRespuesta = mediador.obtenerEntidad(dto.id_opcionRespuesta());

        List<Ponderacion> ponderaciones = ponderacionValidators.construirPonderaciones(dto.ponderaciones(), opcionRespuesta.getOpciones(), dto.id_opcionRespuesta());
        Pregunta nueva = Pregunta.crear(normalizado.get("titulo"), normalizado.get("pregunta"), dto, opcionRespuesta, f, ponderaciones);

        Pregunta guardada = dao.guardar(nueva);
        return new preguntaDto(guardada);}
//---------------------------------------------------------------------------------------
    /**
     * Validar Si existe al menos un criterio de busqueda o lanzar una excepcion
     * Delegar la responsabilidad al dao y esperar resultados
     * Retornar lista con elementos o vacia al controlador
     */

    public List<preguntaDto> buscar(preguntaFiltrosDto dto) {
        validators.validarCriterio(dto);

        List<Pregunta> resultados = dao.buscar(dto);
        return resultados
                .stream()
                .map(preguntaDto::new)
                .toList();}

    //---------------------------------------------------------------------------------------
    /**
     * Actualiza una pregunta persistida.
     *
     * <p>El proceso contempla las siguientes operaciones:
     *
     * <ol>
     *     <li>Validar que la entidad se encuentre persistida.</li>
     *     <li>Validar las modificaciones de título, pregunta y descripción.</li>
     *     <li>Comprobar la disponibilidad del nuevo título y contenido de la pregunta
     *         dentro del contexto del factor y la competencia asociados.</li>
     *     <li>Validar los cambios en la configuración de {@link OpcionRespuesta}:
     *         <ul>
     *             <li>Si cambia la configuración, validar las ponderaciones recibidas
     *                 y construir las nuevas ponderaciones.</li>
     *             <li>Si se conserva la configuración, validar las modificaciones
     *                 de las {@link Ponderacion} y construir su actualización.</li>
     *         </ul>
     *     </li>
     *     <li>Actualizar la información de la entidad en la base de datos.</li>
     * </ol>
     */
    public preguntaDto actualizar(Long id, preguntaDtoCreate dto) {

        Pregunta actual = this.obtenerEntidad(id);


        Map<String, String> modificaciones = validators.validarModificacion(actual, dto);

        OpcionRespuesta nueva=null;
        if(!actual.getOpcionRespuesta().getId().equals(dto.id_opcionRespuesta())){
            nueva=mediador.obtenerEntidad(dto.id_opcionRespuesta());}

            OpcionRespuesta nuevaOpcion = validators.validarOpcionRespuesta(actual, dto,nueva);
        ponderacionValidators.ValidarPuntaje(dto.ponderaciones());

        List<Ponderacion> ponderaciones = null;

        if (nuevaOpcion != null) {
            ponderaciones = ponderacionValidators.construirPonderaciones(
                    dto.ponderaciones(),
                    nuevaOpcion.getOpciones(),
                    nuevaOpcion.getId());

        } else {
            boolean modificacionPonderaciones =
                    ponderacionValidators.validarModificacionPonderaciones(
                            actual.getPonderaciones(),
                            dto.ponderaciones());

            if (modificacionPonderaciones) {
                ponderaciones = ponderacionValidators.construirPonderaciones(
                        dto.ponderaciones(),
                        actual.getOpcionRespuesta().getOpciones(),
                        actual.getOpcionRespuesta().getId());
            }
        }
        Pregunta actualizada = dao.actualizar(
                actual,
                (modificaciones.isEmpty()) ? null : modificaciones,
                nuevaOpcion,
                ponderaciones);

        return new preguntaDto(actualizada);}
//---------------------------------------------------------------------------------------
    /**
     * Listar todas las tuplas persistidad de la Entidad
     */
    public List<preguntaDto> obtenerTodos() {
        return dao.obtenerTodos().stream()
                .map(preguntaDto::new)
                .toList();}
//---------------------------------------------------------------------------------------
    /**
     *  Validar existencia de la entidad
     *  Validar que no este siendo usada en el sistema
     *  Crear Auditoria
     *  Delegar eliminacion logica de la pregunta al DAO {@link PreguntaDaoImpl}
     *
     * ************Agregar validaciones de eliminacion cuando implemente el cuestionario****************

     */
    public void eliminarPorFactor(Long id){
        List<Pregunta> preguntas=dao.obtenerPorFactor(id);
        preguntas.forEach(pregunta ->
                {
                    //añadir logica que valide si alguna de las preguntas esta en uso para lanzar una excepcion
                });
        preguntas.forEach(pregunta ->
        {
            Long idPregunta=pregunta.getId();
            this.eliminar(idPregunta);
        });

        }





    public void eliminar(Long id) {
        Pregunta eliminar = this.obtenerEntidad(id);
        LocalDateTime fecha = LocalDateTime.now();
        auditoriaDtoCreate aux = new auditoriaDtoCreate(null, eliminar.getNombre(), tipoAuditoria.PREGUNTA, fecha, "Juan pepe");
        auditoria.crearAuditoria(aux);
        dao.eliminar(eliminar);
    }
//---------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------

    /**
     * Obtener Pregunta en un formato adecuado  para el controlador
     */
        public preguntaDto obtenerPorId(Long id) {
            Pregunta p=this.obtenerEntidad(id);
        return new preguntaDto(p);
    }
    /**
     * Obtener Entidad para otro servicio que lo requiera
     */
    public Pregunta obtenerEntidad(Long id) {
            return    dao.obtenerPorId(id).orElseThrow(() ->
                    new EntityNotFoundException("No se encontró la entidad con el id: "+id+" en el sistema"));

        }

    /**
     * Validar existencia de otra entidad para otro servicio que lo requiera
     */

    public void validarEntidad(Long id) {
        dao.existePorId(id);
    }

    /**
     *{@inheritDoc}
     */
    public boolean existeOpcionRespuestaEnUso(Long id) {
        return dao.existeOpcionRespuestaEnuso(id);
    }



}