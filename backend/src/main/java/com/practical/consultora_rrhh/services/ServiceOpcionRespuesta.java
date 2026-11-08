package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.request.opcionRespuestaDtoCreate;
import com.practical.consultora_rrhh.dto.response.auditoriaDto;
import com.practical.consultora_rrhh.dto.response.opcionDto;
import com.practical.consultora_rrhh.dto.response.opcionRespuestaDto;
import com.practical.consultora_rrhh.excepciones.genericas.ColeccionNotFoundException;
import com.practical.consultora_rrhh.excepciones.genericas.EntidadEnUsoException;
import com.practical.consultora_rrhh.interfaces.dao.IOpcionRespuestaDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceOpcionRespuesta;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.Opcion;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import com.practical.consultora_rrhh.validaciones.OpcionRespuestaValidators;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  Servicio encargado de gestionar la lógica de negocio y las validaciones
 * pertinentes a una {@link OpcionRespuesta}.
 *
 * <p>Las validaciones comprenden aspectos relacionados con el formato,
 * tipo de datos y restricciones de unicidad de determinados campos.</p>
 *
 * <p>También gestiona las operaciones y validaciones relacionadas con la
 * entidad {@link Opcion}, cuya existencia está sujeta al ciclo de vida
 * de la {@link OpcionRespuesta}.</p>
 *
 * <p>Se delegan las validaciones en clases Validators para mantener el
 * código organizado y facilitar su mantenimiento.</p>
 *
 * @author  Besigniano Agustin
 * @Since 1.0
 */
@Service
public class ServiceOpcionRespuesta implements IserviceOpcionRespuesta {

     private final IOpcionRespuestaDao dao;
    private final OpcionRespuestaValidators validators;
    private final ServiceAuditoria serviceAuditoria;
    private final IservicePregunta servicePregunta;

    public ServiceOpcionRespuesta(IOpcionRespuestaDao dao, OpcionRespuestaValidators validators, ServiceAuditoria serviceAuditoria, IservicePregunta servicePregunta) {
        this.dao = dao;
        this.validators = validators;
        this.serviceAuditoria = serviceAuditoria;
        this.servicePregunta = servicePregunta;
    }
    //--------------------------------------------------------------------------------

    /**
     Valida el formato de los campos y verifica que no existan conflictos
     * de duplicidad en la base de datos.
     *
     * <p>Ante cualquier incumplimiento, lanza las excepciones personalizadas
     * correspondientes. Si las validaciones son satisfactorias, registra una
     * nueva {@link OpcionRespuesta} junto con su lista de {@link Opcion}.</p>
     */

    public opcionRespuestaDto guardar(opcionRespuestaDtoCreate dto) {

        validators.existeNombre(TextoUtils.normalizar(dto.nombre()));
        validators.validarFormatoOpciones(dto.opciones());

        OpcionRespuesta entidad = new OpcionRespuesta(dto);

        OpcionRespuesta guardada = dao.guardar(entidad);

        return new opcionRespuestaDto(guardada);
    }
//--------------------------------------------------------------------------------

    /**
     * La siguiente funcion se encarga de actualizar(parcialmente) la entidad persistida en la bdd.
     *
     * @param id Identificador de la entidad persistida en la bdd a modificar. Verificamos existencia como primer validacion
     * @return
     */

    public opcionRespuestaDto actualizar(Long id, opcionRespuestaDtoCreate dto) {

        OpcionRespuesta actual = dao.obtenerPorId(id);

       boolean descripcion=validators.validarFormatoModificaciones(id,actual,dto);

        List<opcionDto> aux = validators.ValidarAlteracionOpcion(id, actual.getOpciones(), dto.opciones());
        OpcionRespuesta nueva;

        if(descripcion || !aux.isEmpty()) {
            nueva = dao.actualizar(descripcion ? dto.descripcion() : null, actual, aux);
            return new opcionRespuestaDto(nueva);
        }
        return new opcionRespuestaDto(actual);

        }

    //----------------------------------------------------------

    /**
     * Listar todas las opciones de respuesta existentes de la Base de datos
     * @return Puede contener elementos o representar la lista vacia
     */

    public List<opcionRespuestaDto> obtenerTodos() {
        List<opcionRespuestaDto> lista;
        lista = dao.obtenerTodos()
                .stream()
                .map(opcionRespuestaDto::new)
                .toList();
        if (lista.isEmpty()) {
            throw new ColeccionNotFoundException("No se encontraron resultados de busquda");
        }
        return lista;
    }

    /**
     * Validar existencia de la entidad en la BDD y eliminarla
     *Implementar: Generar registro de auditoria
     * @param id
     */

    public void eliminar(Long id) {
        OpcionRespuesta aux = dao.obtenerPorId(id);
if(servicePregunta.existeOpcionRespuestaEnUso(id)){
    throw new EntidadEnUsoException("La opcion de respuesta esta siendo usada por una pregunta por lo tanto no puede ser eliminada");
    }
auditoriaDtoCreate dto = new auditoriaDtoCreate(null,aux.getNombre(), tipoAuditoria.OPCION_RESPUESTA, LocalDateTime.now(),"pepe Juan");
serviceAuditoria.crearAuditoria(dto);
        dao.eliminar(aux);
    }

    /**
     * Obtener una opcion de respuesta o lanzar mensaje si no existe
     */

    public opcionRespuestaDto obtenerPorId(Long id) {

        OpcionRespuesta aux = dao.obtenerPorId(id);

        return new opcionRespuestaDto(aux);
    }

    public boolean validarExistencia(Long id) {
        dao.existePorId(id);
        return true;  }

    /**
     * Obtiene una lista de opciones de respuesta cuyo nombre coincide parcialmente
     * con el texto proporcionado.
     *
     * @param nombre texto utilizado como filtro de búsqueda por coincidencia parcial.
     */
    public List<opcionRespuestaDto> obtenerPorNombre(String nombre) {
        List<opcionRespuestaDto> lista;
        lista = dao.obtenerPorNombreParcial(nombre)
                .stream()
                .map(opcionRespuestaDto::new)
                .toList();
        if (lista.isEmpty()) {
             throw new ColeccionNotFoundException("No se encontraron resultados de busquda");
        }
        return lista;
    }

//--------------------------------------------------------------
//---------------------Consultas entre servicio-------------------------

    @Override
    public OpcionRespuesta obtenerEntidad(Long id) {
        return  dao.obtenerPorId(id);
    }

    @Override
    public void validarEntidad(Long id) {
        dao.existePorId(id);
    }
}