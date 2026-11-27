package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.response.competenciaDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerCompetencia;
import com.practical.consultora_rrhh.interfaces.services.IserviceCompetencia;
import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import com.practical.consultora_rrhh.services.ServiceCompetencia;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que contiene las peticiones para gestionar competencias
 * Contiene todos los metodos de Creacion,Actualizacion,Eliminacion
 * Funciones para listar las competencias del sistema
 * Funciones para buscar competencias
 *
 * @author Besigniano Agustin
 * @since 1.0
 */
@Controller
@RestController
@RequestMapping("/gestionar-competencias")
public class ControllerCompetencia implements IcontrollerCompetencia {

    private final IserviceCompetencia service;

    public ControllerCompetencia(IserviceCompetencia service) {
        this.service = service;
    }

    /**
     * Petición para crear una nueva competencia.
     * La lógica de negocio y las validaciones correspondientes
     * se delegan en {@link ServiceCompetencia}.
     * Se utiliza una abstracción para desacoplar el controlador
     * de la implementación del servicio.
     *
     * @param dto datos necesarios para crear la competencia.
     *            Para conocer los campos consultar {@link competenciaDtoCreate}
     * @return competencia creada
     */
    @PostMapping("/new")
    public ResponseEntity<competenciaDto> guardar(@Valid @RequestBody competenciaDtoCreate dto) {
        competenciaDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    /**
     * Actualizar campos particulares de la competencia
     * El codigo de la misma no es modificable El intentar modificarlo provoca una excepcion
     *
     * @param id Recibimos el id de la competencia que se desea modificar
     *           debemos Garantizar su existencia
     *
     */

    @PatchMapping("/update/{id}")
    public ResponseEntity<competenciaDto> actualizar(@PathVariable String id, @Valid @RequestBody competenciaDtoCreate dto) {
        Long identificador = TextoUtils.identificador(id);

        return ResponseEntity.ok(service.actualizar(identificador, dto));
    }

    /**
     * Peticion para eliminar una competencia del sistema recibimos la competencia que se desea eliminar
     * Se recibe el consultor responsable de dicha peticion
     * En el caso de un resultado existo se genera un registro de auditoria {@link Auditoria}
     */

    @Override
    @DeleteMapping("/delete/{id}/responsable/{idConsultor}")
    public ResponseEntity<String> eliminar(@PathVariable String id, @PathVariable String idConsultor) {
        Long identificador = TextoUtils.identificador(id);
        Long consultor = TextoUtils.identificador(idConsultor);
        String respuesta = service.eliminar(identificador, consultor);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Listar todas las competencias del sistema
     */
    @GetMapping("/lista")
    public ResponseEntity<List<competenciaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /**
     * Obtener un competencia a traves de su id
     */
    @GetMapping("/{id}")
    public ResponseEntity<competenciaDto> obtenerPorId(@PathVariable String id) {
        Long identificador = TextoUtils.identificador(id);
        return ResponseEntity.ok(service.obtenerPorId(identificador));}

    /**
     * Buscar competencia a traves del nombre de la competencia
     */
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<competenciaDto>> buscar(@PathVariable String nombre) {
        return ResponseEntity.ok(service.buscar(nombre));
    }
}



