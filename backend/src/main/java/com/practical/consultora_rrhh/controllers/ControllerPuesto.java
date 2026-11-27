package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.puestoFiltrosDto;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.request.puestoDtoCreate;
import com.practical.consultora_rrhh.dto.response.puestoDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerPuesto;
import com.practical.consultora_rrhh.interfaces.services.IservicePuesto;
import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import com.practical.consultora_rrhh.services.ServiceFactor;
import com.practical.consultora_rrhh.services.ServicePuesto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controlador de puestos responde a la creacion,modificacion y eliminacion
 * Tiene funciones de listar todas y filtros de busqueda para obtener por nombre
 * o recuperar una sola en base a su ID
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Controller
@RestController
@RequestMapping("/puestos")

public class ControllerPuesto implements IcontrollerPuesto {
    private final IservicePuesto service;
    public ControllerPuesto(IservicePuesto servicepuesto) {
        this.service = servicepuesto;
    }

    /**
     * Petición para crear un nueva puesto.
     * La lógica de negocio y las validaciones correspondientes
     * se delegan en {@link ServicePuesto}.
     * Se utiliza una abstracción para desacoplar el controlador
     * de la implementación del servicio.
     *
     * @param dto datos necesarios para crear el factpr.
     *            Para conocer los campos consultar {@link factorDtoCreate}
     */
    @PostMapping("/new")
    public ResponseEntity<puestoDto> guardar(@Valid @RequestBody puestoDtoCreate dto) {
        puestoDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }

    /**
     * Actualizar campos particulares del puesto
     * El codigo no es editable
     * El nombre debe estar disponible
     *
     * @param id Recibimos el id del factor que se desea modificar
     *           debemos Garantizar su existencia
     */

    @PatchMapping("/update/{id}")
    public ResponseEntity<puestoDto> actualizar(@PathVariable String id, @Valid @RequestBody puestoDtoCreate dto) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.actualizar(identificador, dto));
    }

    /**
     * Peticion para eliminar un puesto del sistema.
     * Recibimos el puesto que se desea eliminar
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
     * Listar todos los puestos del sistema
     * @return
     */
    @GetMapping("/lista")
    public ResponseEntity<List<puestoDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /**
     * Obtener un puesto mediante su identificador
     */
    @GetMapping("/{id}")
    public ResponseEntity<puestoDto> obtenerPorId(@PathVariable String id) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.obtenerPorId(identificador));
    }

    /**
     * Buscar puesto a traves de filtros de busqueda parametrizados
     * Para saber mas consultar {@link puestoFiltrosDto}
     */
    @Override
    @GetMapping("/buscar")
    public ResponseEntity<List<puestoDto>> buscar(@ModelAttribute puestoFiltrosDto dto) {
        List<puestoDto> result=service.buscar(dto);

    return ResponseEntity.ok(result);
    }
}