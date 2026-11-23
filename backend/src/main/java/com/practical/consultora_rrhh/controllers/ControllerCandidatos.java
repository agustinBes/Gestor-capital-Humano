package com.practical.consultora_rrhh.controllers;

import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.excepciones.genericas.CorruptFileException;
import com.practical.consultora_rrhh.interfaces.auxiliares.auth.Iconsultor;
import com.practical.consultora_rrhh.interfaces.repositorio.archivoRepository;
import com.practical.consultora_rrhh.interfaces.services.IserviceCandidato;
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
 * "id,nombre,apellido,fecha,mail,tipo,documento,nacionalidad,genero,escolaridad".
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
 * @author Besigniano Agustin
 * @since  1.0
 */
@Controller
@RestController
@RequestMapping("/gestionar-candidatos/")
public class ControllerCandidatos {
private List<HistorialArchivo> archivosImportados=new ArrayList<>();
    private final IserviceCandidato service;
    private final Iconsultor iconsultor;
private final archivoRepository repositorio;
    @PostConstruct
    public void init(){
        archivosImportados=repositorio.findAll();    }
    public ControllerCandidatos(IserviceCandidato s, Iconsultor iconsultor, archivoRepository repositorio) {
        this.service = s;
        this.iconsultor = iconsultor;
        this.repositorio = repositorio;}

    @PostMapping(value = "/import/{idConsultor}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<String> importarCandidatos(@RequestParam(required = false) MultipartFile file,@PathVariable String  idConsultor) throws CorruptFileException {
Long consultor= TextoUtils.identificador(idConsultor);
        Consultor c=iconsultor.obtenerConsultor(consultor);
        if (file == null) {
            ClassPathResource resource = new ClassPathResource("candidatos.csv");

            boolean existe = archivosImportados.stream()
                    .anyMatch(archivo ->
                            archivo.getNombre().equals(resource.getFilename()));


            if (existe) {
return ResponseEntity.status(HttpStatus.CONFLICT).body("Este archivo ya fue agregado con anterioridad");}


            String r= service.importarCandidatos(resource);
            HistorialArchivo h=new HistorialArchivo(null, resource.getFilename(), c);
repositorio.save(h);
archivosImportados.add(h);
                return ResponseEntity.ok().body("Importado con exito"+r);
            }


        if (file.isEmpty()) {return ResponseEntity.badRequest().body("El archivo esta vacio");       }

        boolean existe = archivosImportados.stream()
                .anyMatch(archivo ->
                        archivo.getNombre().equals(file.getOriginalFilename()));
        if (existe) {

return ResponseEntity.status(HttpStatus.CONFLICT).body("Este archivo ya fue agregado con anterioridad");}


       String r= service.importarCandidatos(file.getResource());

        HistorialArchivo h=new HistorialArchivo(null, file.getOriginalFilename(), c);
        repositorio.save(h);
        archivosImportados.add(h);

        return ResponseEntity.ok(r);

    }


@GetMapping("/candidatos")
    public ResponseEntity<List<candidatoDto>> listarCandidatos(){
        List<candidatoDto> candidatos=service.obtenerCandidatos();
        return ResponseEntity.ok(candidatos);
}

@GetMapping("/buscar")
    public ResponseEntity<List<candidatoDto>> buscarCandidatos(@Valid @ModelAttribute candidatoFiltrosDto filtro){
               List<candidatoDto> result=service.buscarCandidatos(filtro);
        return ResponseEntity.ok(result);

}
}