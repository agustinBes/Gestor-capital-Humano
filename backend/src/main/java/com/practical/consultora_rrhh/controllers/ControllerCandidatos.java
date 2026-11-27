package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.dto.request.candidatoDtoAuth;
import com.practical.consultora_rrhh.dto.request.candidatoDtoCreate;
import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;
import com.practical.consultora_rrhh.excepciones.genericas.CorruptFileException;

import com.practical.consultora_rrhh.interfaces.auxiliares.Iconsultor;
import com.practical.consultora_rrhh.interfaces.controllers.IcontrollerCandidato;
import com.practical.consultora_rrhh.interfaces.repositorio.archivoRepository;
import com.practical.consultora_rrhh.interfaces.services.IserviceCandidato;
import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.modelo.clases.Consultor;
import com.practical.consultora_rrhh.modelo.clases.HistorialArchivo;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador encargado de la importación de candidatos mediante archivos CSV.
 *
 * El archivo debe respetar el siguiente formato de columnas:
 * "id,nombre,apellido,fecha,mail,tipoDocumento,documento,nacionalidad,genero,escolaridad".
 *
 * Tanto el identificador como el documento del candidato deben ser únicos,
 * por lo que no se permiten registros que contengan valores repetidos.
 *
 * En el alcance de este trabajo no se implementan los restantes métodos
 * correspondientes a la gestión de candidatos.
 *
 * Considerando el contexto del enunciado, se asume que la importación
 * responde a un proceso de migración de candidatos desde otro sistema
 * utilizado por la empresa.
 * Es por esta razon que los id(representando el Nro de candidato) no son autogenerados,
 * vienen desde el sistema principal
 * @author Besigniano Agustin
 * @since  1.0
 */
@Controller
@RestController
@RequestMapping("/gestionar-candidatos/")
public class ControllerCandidatos implements IcontrollerCandidato {
private List<HistorialArchivo> archivosImportados=new ArrayList<>();
    private final IserviceCandidato service;
    private final Iconsultor iconsultor;
private final archivoRepository repositorio;
private final IserviceCuestionario  serviceCuestionario;
private final    ClassPathResource resource = new ClassPathResource("candidatos.csv");
    @PostConstruct
    public void init(){
        archivosImportados=repositorio.findAll();    }
    public ControllerCandidatos(IserviceCandidato s, Iconsultor iconsultor, archivoRepository repositorio, IserviceCuestionario serviceCuestionario) {
        this.service = s;
        this.iconsultor = iconsultor;
        this.repositorio = repositorio;
        this.serviceCuestionario = serviceCuestionario;
    }

    /**
     * Una entidad que registra el nombre de los archivos importados y niega el acceso a la capa de servicio
     * si ya existe
     * @param archivo representa el nombre del archivo
     * @param c representa el consultor responsable de la importacion
     */

    private void guardarRegistroArchivo(String archivo,Consultor c){
        HistorialArchivo h=new HistorialArchivo(null, archivo, c);
        HistorialArchivo guardado=repositorio.save(h);
        archivosImportados.add(guardado);}

    /**
     * Proporcionamos a modo de prueba un archivo con candidatos para importar pueden enviarlo cargandolo
     * o solo enviar la solicitud y el sistema procede a importarlo la primera vez
     * @throws CorruptFileException gestionar excepciones si fallo al abrir o algo en la lectura del archivo
     */
    private String proporcionarCandidatos(Consultor c) throws CorruptFileException {
        String    r = service.importarCandidatos(resource);
            guardarRegistroArchivo(resource.getFilename(),c);
        return r;}

    /**
     * Peticion que recibe un archivo en formato csv para la importacion de candidatos se debe respetar la columna de encabezado
     */
    @PostMapping(value = "/import/{idConsultor}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importarCandidatos(@RequestParam(required = false) MultipartFile file,@PathVariable String  idConsultor) throws CorruptFileException {

        Long consultor = TextoUtils.identificador(idConsultor);
        Consultor c = iconsultor.obtenerConsultor(consultor);
               boolean existe = archivosImportados.stream()
                    .anyMatch(archivo ->archivo.getNombre().equals(resource.getFilename()));
               //Archivo null y no existe el archivo local
            if (file == null && !existe) {
                proporcionarCandidatos(c);
                return ResponseEntity.ok().body("Importamos un conjunto de datos locales con exito");
            } else {
                //Archivo null y  existe el archivo local
                if (file == null && existe) {
                    return ResponseEntity.ok("No se proporcionó ningun conjunto de datos. Por favor Agregue archivos antes de continuar");
                } else {
                    //archivo vacio y no existe el conjunto local
                    if (file.isEmpty() && !existe) {
                        proporcionarCandidatos(c);
                        return ResponseEntity.ok().body("Importamos un conjunto de datos locales con exito");
                    } else {
                        //archivo vacio y existe el conjunto local
                        if (file.isEmpty()) {
                            return ResponseEntity.badRequest().body("El archivo esta vacio");
                        }
                        //Se envio un archivo y pasa a ser analizado
                        else {
                            existe = archivosImportados.stream()
                                    .anyMatch(archivo ->
                                            archivo.getNombre().equals(file.getOriginalFilename()));
//El archivo enviado ya existe en el sistema
                            if (existe) {
                                return ResponseEntity.status(HttpStatus.CONFLICT).body("Este archivo ya fue agregado con anterioridad");
                            }
//procesar nuevo conjunto de datos

                            String r = service.importarCandidatos(file.getResource());
                            guardarRegistroArchivo(file.getOriginalFilename(), c);

                            return ResponseEntity.ok("Importamos con exito el conjunto de datos" + r);
                        }
                    }}}}

    /**
     * Listar todos los candidatos existentes en el sistema
     */
    @GetMapping("/lista")
    public ResponseEntity<List<candidatoDto>> obtenerTodos(){
        List<candidatoDto> candidatos=service.obtenerTodos();
        return ResponseEntity.ok(candidatos);}

    /**
     * Filtro de busqueda de candidatos. Para saber los campos de busqueda consultar {@link candidatoFiltrosDto}
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<candidatoDto>> buscarCandidatos(@Valid @ModelAttribute candidatoFiltrosDto filtro){
               List<candidatoDto> result=service.buscarCandidatos(filtro);
        return ResponseEntity.ok(result);
}

    /**
     * Operacion no soportada en esta version del sistema
     */
    @Override
    @PostMapping("/new")
    public ResponseEntity<candidatoDto> guardar(candidatoDtoCreate dto) {
        throw new UnsupportedOperationException();
    }

    /**
     * Peticion para acceder al sistema por parte de un candidato. Consultar los campos requeridos en {@link candidatoDtoAuth}
     */
    @Override
    public ResponseEntity<cuestionarioDto> autenticarCandidato(candidatoDtoAuth candidatoDtoAuth) {
        cuestionarioDto cuestionario=serviceCuestionario.obtenerCuestionarioDto(candidatoDtoAuth);
return ResponseEntity.ok(cuestionario);
    }

    /**
     * Operacion no soportada en esta version del sistema
     */
    @Override
    @PatchMapping("/update/{id}")

    public ResponseEntity<candidatoDto> actualizar(String id, candidatoDtoCreate dto) {
       throw new UnsupportedOperationException();
    }

    /**
     * Operacion no soportada en esta version del sistema
     */
    @Override
    @DeleteMapping("/delete/{id}/responsable/{idConsultor}")

    public ResponseEntity<String> eliminar(String id, String idConsultor) {
        throw new UnsupportedOperationException();
    }

    /**
     * Operacion no soportada en esta version del sistema
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<candidatoDto> obtenerPorId(String id) {
        throw new UnsupportedOperationException();
    }

}