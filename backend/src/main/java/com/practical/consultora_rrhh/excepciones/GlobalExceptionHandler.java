package com.practical.consultora_rrhh.excepciones;

import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.SumaPonderacionesInvalidException;
import com.practical.consultora_rrhh.excepciones.genericas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**Controlador de excepciones globales de excepciones genericas para poder gestionar excepciones de multiples clases
 * aprovecha la generacion de excepciones especializadas que heredan de estas genericas
 *
 * @author  Besigniano Agustin
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
//-------------------------------------------------------------------------------------
// ----------------Personalizados---------------------------------
//-------------------------------------------------------------------------------------
     private ResponseEntity<Map<String, Object>> respuesta(HttpStatus s, String mensaje) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", s.value());
        body.put("error", s.getReasonPhrase());
        body.put("mensaje", mensaje);

        return ResponseEntity.status(s).body(body);
    }
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, Object>> invalidFormat(InvalidFormatException e) {
return  respuesta(HttpStatus.BAD_REQUEST, e.getMessage());    }
//Duplicados de nombre y codigo
    @ExceptionHandler(DuplicadoException.class)
    public ResponseEntity<Map<String, Object>> manejarDuplicado(
            DuplicadoException e) {
return respuesta(HttpStatus.CONFLICT,e.getMessage());
          }
          @ExceptionHandler(CorruptFileException.class)
          public ResponseEntity<Map<String, Object>> handleIOException(IOException e){
         return respuesta(HttpStatus.UNPROCESSABLE_CONTENT,e.getMessage());
          }
//Ponderaciones en la pregunta
    @ExceptionHandler(SumaPonderacionesInvalidException.class)
    public ResponseEntity<Map<String, Object>> manejarSumaPonderacionesInvalid(SumaPonderacionesInvalidException e) {

        return respuesta(HttpStatus.BAD_REQUEST,e.getMessage());
    }
    //Ausencia de entidad con id :x
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,Object>> manejarObjectNotFound(EntityNotFoundException e) {

        return respuesta(HttpStatus.NOT_FOUND, e.getMessage());

    }
    @ExceptionHandler(EntidadEnUsoException.class)
    public ResponseEntity<Map<String, Object>> manejarEntidadEnUso(EntidadEnUsoException e){
        return respuesta(HttpStatus.CONFLICT,e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> manejarAtributoInvalido(
            MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        // Obtenemos el primer error de campo del DTO
        FieldError fieldError = bindingResult.getFieldErrors()
                .stream()
                .findFirst()
                .orElse(null);

        String mensaje = "Error de validación en los campos";

        if (fieldError != null) {
            String campo = fieldError.getField(); // Ej: "opciones[1].descripcion" o "nombre"
            String mensajeAnotacion = fieldError.getDefaultMessage(); // Ej: "Se requiere al menos una oración..."

            // Si el error pertenece a una lista de cualquier entidad (contiene corchetes []), lo indicamos claramente
            if (campo.contains("[") && campo.contains("]")) {
                mensaje = "Error en el elemento (" + campo + "): " + mensajeAnotacion;
            } else {
                // Si es un campo plano de la entidad principal
                mensaje = mensajeAnotacion;
            }
        }

        return respuesta(HttpStatus.BAD_REQUEST, mensaje);

    }

    @ExceptionHandler(AtributoInvalidoException.class)
    public ResponseEntity<Map<String,Object>> manejarArgumentoInvalido(AtributoInvalidoException e) {
        return respuesta(HttpStatus.BAD_REQUEST,e.getMessage());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String,Object>> manejarErrorTipo(
            MethodArgumentTypeMismatchException e) {
        return respuesta(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(FiltroVacioException.class)
    public ResponseEntity<Map<String,Object>> manejarFiltro(FiltroVacioException e) {
        return respuesta(HttpStatus.BAD_REQUEST,e.getMessage());
      }
      @ExceptionHandler(IntegridadComprometidaException.class)
      public ResponseEntity<Map<String,Object>> manejarIntegridad(IntegridadComprometidaException e) {
         return respuesta(HttpStatus.BAD_REQUEST,e.getMessage());
      }
    //-----------------------------------------------------------------------
    @ExceptionHandler({HttpMessageNotReadableException.class, RuntimeJsonMappingException.class})
    public ResponseEntity<Map<String,Object>> manejarNumberFormat(Exception e) {
        String mensajeAmigable = "El formato del JSON es inválido o contiene valores malformados.";

        if (e.getMessage().contains("Unrecognized token") || e.getMessage().contains("Unexpected character")) {
            return respuesta(HttpStatus.BAD_REQUEST, mensajeAmigable);
        }

// Cubre decimales (con accept-float-as-int=false), booleans o strings en campos numéricos
        if (e.getMessage().contains("Cannot coerce") || e.getMessage().contains("Cannot deserialize value of type")) {
            return respuesta(HttpStatus.BAD_REQUEST, "Tipo de dato inválido. Se esperaba un número entero");
        }

// Cubre números enteros que superan el rango (-32768 a 32767)
        if (e.getMessage().contains("out of range")) {
            return respuesta(HttpStatus.BAD_REQUEST, "El valor está fuera del rango permitido para un short (1 a 32767).");
        }

        return respuesta(HttpStatus.BAD_REQUEST,e.getMessage());
         }


}