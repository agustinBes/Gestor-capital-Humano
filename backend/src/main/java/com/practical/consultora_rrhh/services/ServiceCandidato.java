package com.practical.consultora_rrhh.services;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.FiltrosBusqueda.candidatoFiltrosDto;
import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.excepciones.genericas.*;
import com.practical.consultora_rrhh.interfaces.dao.IcandidatoDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceCandidato;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import com.practical.consultora_rrhh.services.mediator.CandidatoCuestionarioMediator;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la importacion,validacion {@link candidatoDto}
 * y persistencia de los candidatos
 * Para procesar los archivos CSV se utiliza la biblioteca Jackson Dataformat CSV,
 * que permite deserializar los registros directamente en objetos Java.

 * Adicionalmente, se utiliza la dependencia Jackson Datatype JSR310 para
 * permitir la conversión de campos temporales de Java, como LocalDate y
 * LocalDateTime, durante el proceso de deserialización.

 * Por motivos prácticos y considerando el alcance de este trabajo, queda
 * fuera de la implementación la gestión individual de candidatos como entidad.

 * Por lo tanto, no se contemplan las operaciones de creación individual,
 * actualización ni eliminación de candidatos, limitándose su incorporación
 * al proceso de importación masiva desde archivos CSV.
 * @author  Besigniano Agustin
 * @since 1.0
 */

@Service
public class ServiceCandidato implements IserviceCandidato {
    private  int creados=0;
private final Validator validador;
private  final IcandidatoDao dao;
private final CandidatoCuestionarioMediator cuestionarioaMediator;

    public ServiceCandidato(Validator validador, IcandidatoDao dao, CandidatoCuestionarioMediator cuestionarioaMediator) {
        this.dao = dao;
        this.validador = validador;
        this.cuestionarioaMediator = cuestionarioaMediator;
    }
@PostConstruct
public void inicializar(){

        creados=dao.obtenerTodos().size();
        cuestionarioaMediator.registrarServicioB(this);
}


    @Override
    public candidatoDto guardar(candidatoFiltrosDto dto) {
        throw  new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public candidatoDto actualizar(Long aLong, candidatoFiltrosDto dto) {
        throw  new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<candidatoDto> obtenerTodos() {
        return dao.obtenerTodos().stream()
                .map(candidatoDto::new)
                .toList();
    }

    @Override
    public String eliminar(Long aLong, Long idConsultor) {
        throw  new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public candidatoDto obtenerPorId(Long aLong) {
        throw  new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public String importarCandidatos(Resource file) throws CorruptFileException {
        List<Long> nrosCandidatos = new ArrayList<>();
int existe=0;
        try {
            InputStream is = file.getInputStream();
            List<candidatoDto> c = transformarCsv(is);
            List<Candidato> candidatos = c.stream().map(Candidato::new).toList();

for (Candidato candidato : candidatos) {
    Long id=candidato.getId();
Optional<Candidato> aux= dao.obtenerPorId(id);
if(aux.isEmpty()){
    dao.guardar(candidato);
    creados=creados+1;
}
else{existe=existe+1;
nrosCandidatos.add(id);}
}
if(existe>0){return "Algunos Nros de candidatos ya existen en el sistema.Verifica si se trata de un nuevo candidato o intentas crear el mismo devuelta" +
        nrosCandidatos.toString();}

return  "Se crearon todos los candidatos";
        } catch (IOException e) {
            throw new CorruptFileException(HttpStatus.UNPROCESSABLE_CONTENT,"El archivo esta corrupto o dañado");
        }
    }




    public List<candidatoDto> transformarCsv(InputStream inputStream) throws CorruptFileException {
        List<candidatoDto> candidatos = new ArrayList<>();
        CsvSchema candidatoSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.registerModule(new JavaTimeModule());


        try (MappingIterator<candidatoDto> candidato = csvMapper.readerFor(candidatoDto.class)
                .with(candidatoSchema)
                .readValues(inputStream)) {
            int numeroFila = 1;
            while (candidato.hasNext()) {
                numeroFila++;
                // Aquí Jackson intenta convertir la fila actual a candidatoDtoAuth
                candidatoDto fila = candidato.next();
                Set<ConstraintViolation<candidatoDto>> errores = validador.validate(fila);
                System.out.println(fila);
                if (!errores.isEmpty()) {
                    // Puedes acumular los errores o lanzar una excepción inmediatamente
                    String detalle = errores.stream()
                            .map(e -> e.getPropertyPath() + ": " + e.getMessage())
                            .collect(Collectors.joining(","));
                    throw new AtributoInvalidoException("Error en la fila " + numeroFila + " -> " + detalle);
                }
                candidatos.add(fila);
            }

            return candidatos;
        } catch (AtributoInvalidoException e) {
            throw e; // La deja pasar intacta hacia tu ControllerAdvice
     } catch (
    RuntimeJsonMappingException e) {
        throw e; // Deja que lo maneje @ExceptionHandler y devuelva 400
    } catch (Exception e) {
        throw new CorruptFileException(HttpStatus.UNPROCESSABLE_CONTENT,"Error al procesar el archivo CSV: " + e.getMessage());
    }
    }
    @Override
    public List<candidatoDto> buscarCandidatos(candidatoFiltrosDto filtro) {
        List<candidatoDto> result=new ArrayList<>();
boolean validacion=validarFiltros(filtro);
if(validacion){
result=dao.buscar(filtro)
            .stream()
            .map(candidatoDto::new)
            .toList();
if(result.isEmpty()){
    throw new ColeccionNotFoundException();}
return result;}

throw new FiltroVacioException();

    }



    @Override
    public List<Candidato> obtenerCandidatos(List<Long> candidatos) {
         int cantidad=candidatos.size();
     List<Candidato> lista=dao.obtenerCandidatosPorId(candidatos);
if(cantidad!=lista.size()){
    throw new ColeccionCorruptaException("Hay candidatos que no existen en el sistema, Error de integridad referencial");
}

return lista;
   }

    public boolean validarFiltros(candidatoFiltrosDto filtro) {
return filtro.id()!=null | TextoUtils.tieneContenido(filtro.apellido()) | TextoUtils.tieneContenido(filtro.nombre());}



}


