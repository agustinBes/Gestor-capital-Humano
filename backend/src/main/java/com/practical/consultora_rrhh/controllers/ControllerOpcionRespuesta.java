package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.request.factorDtoCreate;
import com.practical.consultora_rrhh.dto.request.opcionRespuestaDtoCreate;
import com.practical.consultora_rrhh.dto.response.opcionRespuestaDto;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerOpcionRespuesta;
import com.practical.consultora_rrhh.interfaces.services.IserviceOpcionRespuesta;
import com.practical.consultora_rrhh.modelo.clases.Auditoria;
import com.practical.consultora_rrhh.modelo.clases.Opcion;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.services.ServiceFactor;
import com.practical.consultora_rrhh.services.ServiceOpcionRespuesta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controlador de opciones de respuesta responde a la creacion,modificacion y eliminacion
 * Tiene funciones de listar  y filtros de busqueda para obtener por nombre
 * o recuperar una sola en base a su ID
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Controller
@RestController
@RequestMapping("/opciones-de-respuesta")
public class ControllerOpcionRespuesta implements IcontrollerOpcionRespuesta {
    private final IserviceOpcionRespuesta service;

    public ControllerOpcionRespuesta(IserviceOpcionRespuesta service) {
        this.service = service;
    }
    /**
     * Petición para crear una nueva opcion de respuestaa.
     * La lógica de negocio y las validaciones correspondientes
     * se delegan en {@link ServiceOpcionRespuesta}.
     * Se utiliza una abstracción para desacoplar el controlador
     * de la implementación del servicio.
     *
     * @param dto datos necesarios para crear el factpr.
     *            Para conocer los campos consultar {@link opcionRespuestaDtoCreate}
     */

      @Override
      @PostMapping("/new")
    public ResponseEntity<opcionRespuestaDto> guardar(@Valid @RequestBody opcionRespuestaDtoCreate dto) {
        opcionRespuestaDto aux = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(aux);
    }
    /**
     * Actualizar campos particulares de la opcion de respuesta {@link OpcionRespuesta}
     * El nombre no es editable una vez creada la opcion
     * La cantidad de opciones vinculadas a la opcion de respuesta {@link Opcion }
     * No es modificable una vez creada las mismas
     *
     */
    @Override
    @PatchMapping("/update/{id}")
    public ResponseEntity<opcionRespuestaDto> actualizar(@PathVariable String id, @Valid @RequestBody opcionRespuestaDtoCreate dto) {
       Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.actualizar(identificador, dto));
    }
    /**
     * Peticion para eliminar una opcion de respuesta del sistema.
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
     * Listar todas las opciones de respuesta del sistema
     */
    @Override
    @GetMapping("/lista")
    public ResponseEntity<List<opcionRespuestaDto>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /**
     * Obtener una opcion de respuesta mediante el identificador
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<opcionRespuestaDto> obtenerPorId(@PathVariable String id) {
        Long identificador= TextoUtils.identificador(id);

        return ResponseEntity.ok(service.obtenerPorId(identificador));
    }

    /**
     * Buscar opciones de respuesta a traves de su nombre
     * Si no recibimos parametro retornamos todas
     */
    @Override
    @GetMapping({"/nombre/{nombre}", "/nombre/"})

    public ResponseEntity<List<opcionRespuestaDto>> obtenerPorNombre(@PathVariable(required = false)
                                                                         String nombre) {
        return ResponseEntity.ok(service.obtenerPorNombre(nombre));
    }
}
