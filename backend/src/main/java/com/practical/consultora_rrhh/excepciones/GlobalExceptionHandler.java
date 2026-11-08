package com.practical.consultora_rrhh.excepciones;

import com.practical.consultora_rrhh.excepciones.especializadas.atributos.SumaPonderacionesInvalidException;
import com.practical.consultora_rrhh.excepciones.genericas.DuplicadoException;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler(DuplicadoException.class)
    public ResponseEntity<Map<String,String>> manejarDuplicado(
            DuplicadoException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "status", "409",
                        "error", HttpStatus.CONFLICT.getReasonPhrase(),
                        "message", e.getMessage()
                ));
    }
    @ExceptionHandler(SumaPonderacionesInvalidException.class)
    public ResponseEntity<Map<String,String>> manejarSumaPonderacionesInvalid(SumaPonderacionesInvalidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                  .body(Map.of(
                        "status", "404",
                        "error", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> manejarAtributoInvalido(
            MethodArgumentNotValidException e) {

        String mensaje = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        String respuesta = """
            {
                "status": 400,
                "error": "Bad Request",
                "message": "%s"
            }
            """.formatted(mensaje);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> manejarObjectNotFound(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> manejarErrorTipo(
            MethodArgumentTypeMismatchException e) {

        return ResponseEntity.badRequest()
                .body("El parámetro '" + e.getName()
                        + "' no corresponde con el tipo de dato solicitado.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> manejarNumberFormat(HttpMessageNotReadableException e) {

        return ResponseEntity.badRequest().body("El formato de datos recibidos no coincide con el solicitado");
    }
}