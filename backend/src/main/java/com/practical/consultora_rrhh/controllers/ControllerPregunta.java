package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.preguntaFiltrosDto;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.request.preguntaDtoCreate;
import com.practical.consultora_rrhh.dto.response.preguntaDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerPregunta;
import com.practical.consultora_rrhh.interfaces.services.IservicePregunta;
import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import com.practical.consultora_rrhh.modelo.clases.Opcion;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.services.ServicePregunta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que gestiona la creacion,modificacion y eliminacion de preguntas
 * Cuenta con peticiones para buscar a traves de parametros especificos de la pregunta
 * Funciones para listar todas las preguntas
 */
@Controller
@RestController
@RequestMapping("/gestionar-preguntas")
public class ControllerPregunta implements IcontrollerPregunta {

private final IservicePregunta service;

    public ControllerPregunta(IservicePregunta service) {
        this.service = service;
    }
    /**
     * Petición para crear una nueva pregunta en el sistema.
     * La lógica de negocio y las validaciones correspondientes
     * se delegan en {@link ServicePregunta}.
     * Se utiliza una abstracción para desacoplar el controlador
     * de la implementación del servicio.
     *
     * @param dto datos necesarios para crear el factpr.
     *            Para conocer los campos consultar {@link preguntaDtoCreate}
     *            Se elige una opcion de respuesta {@link OpcionRespuesta}
     *            y se asigna una ponderacion a cada una de las opciones vinculadas a esa opcion de respuesta {@link Opcion}
     La suma de estas ponderaciones debe dar 10 en total y no pueden ser negativas
     */
    @Override
    @PostMapping("/new")
    public ResponseEntity<preguntaDto> guardar(@Valid @RequestBody preguntaDtoCreate dto) {
        preguntaDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    /**
     * Peticion para modificar campos de la pregunta
     * El codigo no es editable
     * El factor y la competencia asociadas a la misma no son editables
     */
    @Override
    @PatchMapping("/update/{id}")
    public ResponseEntity<preguntaDto> actualizar(@PathVariable String id, @Valid @RequestBody preguntaDtoCreate dto) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.actualizar(identificador, dto));
    }
    /**
     * Peticion para eliminar una pregunta del sistema.
     * Recibimos la pregunta que se desea eliminar
     * Se recibe el consultor responsable de dicha peticion
     * En el caso de un resultado existo se genera un registro de auditoria {@link Auditoria}
     */
    @Override
    @DeleteMapping("/delete/{id}/responsable/{idConsultor}")
    public ResponseEntity<String> eliminar(@PathVariable String id,  @PathVariable  String idConsultor) {
        Long identificador= TextoUtils.identificador(id);
        Long consultor= TextoUtils.identificador(idConsultor);
        String respuesta=  service.eliminar(identificador, consultor);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Obtener todas las preguntas existentes
     */
    @Override
    @GetMapping("/lista")
    public ResponseEntity<List<preguntaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /**
     * Obtener preguntas mediante su identificador
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<preguntaDto> obtenerPorId(@PathVariable String id) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.obtenerPorId(identificador));
    }

    /**
     * Funcion con parametros de busqueda personalizados  para la pregunta
     * Para saber mas sobre los campos de busqueda revisar {@link preguntaFiltrosDto}
     */
    @Override()
@GetMapping("/buscar/")
    public ResponseEntity<List<preguntaDto>> buscar(@ModelAttribute preguntaFiltrosDto dto) {
        return ResponseEntity.ok(service.buscar(dto));
    }
}



