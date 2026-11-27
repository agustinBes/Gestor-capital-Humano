package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.factorFiltrosDto;
import com.practical.consultora_rrhh.dto.request.competenciaDtoCreate;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.response.factorDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerFactor;
import com.practical.consultora_rrhh.interfaces.services.IserviceFactor;
import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import com.practical.consultora_rrhh.services.ServiceCompetencia;
import com.practical.consultora_rrhh.services.ServiceFactor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/** Controlador de Factores responde a la creacion,modificacion y eliminacion
 * Tiene funciones de listar todas y filtros de busqueda para obtener por nombre
 * o recuperar una sola en base a su ID
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Controller
@RestController
@RequestMapping("/factores")

public class ControllerFactor implements IcontrollerFactor {
    private final IserviceFactor service;
    public ControllerFactor(IserviceFactor serviceFactor) {
        this.service = serviceFactor;
    }

    /**
     * Petición para crear un nueva Factor.
     * La lógica de negocio y las validaciones correspondientes
     * se delegan en {@link ServiceFactor}.
     * Se utiliza una abstracción para desacoplar el controlador
     * de la implementación del servicio.
     *
     * @param dto datos necesarios para crear el factpr.
     *            Para conocer los campos consultar {@link factorDtoCreate}
     */
    @PostMapping("/new")
    public ResponseEntity<factorDto> guardar(@Valid @RequestBody factorDtoCreate dto) {
        factorDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    /**
     * Actualizar campos particulares del factor
     * El codigo no es editable
     * El nombre debe estar disponible
     * El nro de orden debe estar disponible
     *
     * @param id Recibimos el id del factor que se desea modificar
     *           debemos Garantizar su existencia
     *
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity<factorDto> actualizar(@PathVariable String id, @Valid @RequestBody factorDtoCreate dto) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.actualizar(identificador, dto));
    }

    /**
     * Peticion para eliminar un factor del sistema.
     * Recibimos el factor que se desea eliminar
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
     * Listas los factores del sistemas
     */
    @GetMapping("/lista")
    public ResponseEntity<List<factorDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /**
     * Obtener un factor mediante su id
     */

    @GetMapping("/{id}")
    public ResponseEntity<factorDto> obtenerPorId(@PathVariable String id) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.obtenerPorId(identificador));
    }

    /**
     * Buscar factore para saber mas sobre las compos de busqueda revisar{@link factorFiltrosDto}
     */
    @GetMapping("/buscar")

    public ResponseEntity<List<factorDto>> buscar(@ModelAttribute factorFiltrosDto dto) {
                return ResponseEntity.ok(service.buscar(dto));
    }
}