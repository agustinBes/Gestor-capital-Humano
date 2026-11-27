package com.practical.consultora_rrhh.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import com.practical.consultora_rrhh.modelo.enums.tipoDocumento;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Formato para la importacion decandidatos coincidiendo con el esquema de los archivos CSV
 * requeridos para permitir importar mediante archivos
 */
public record candidatoDto(
        @NotNull(message = "El id es obligatorio")
        @Positive(message = "Se espera un número entero positivo")
        @Min(value = 1, message = "El id debe ser mayor o igual a 1")
        Long id,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s'-]+$", message = "El nombre contiene caracteres no válidos")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
        @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s'-]+$", message = "El apellido contiene caracteres no válidos")
        String apellido,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate fecha,

        @NotBlank(message = "El mail es obligatorio")
        @Email(message = "El formato del mail no es válido")
        @Size(max = 150, message = "El mail no puede superar los 150 caracteres")
        String mail,

        @NotBlank(message = "El tipoDocumento de documento es obligatorio")
        @Pattern(regexp = "^(DNI|LE|LC|PP)$", message = "El tipoDocumento de documento debe ser DNI, LE, LC o PP")
        String tipo,

        @NotBlank(message = "El número de documento es obligatorio")
        @Pattern(regexp = "^[0-9A-Za-z]{6,15}$", message = "El documento debe tener entre 6 y 15 caracteres alfanuméricos sin puntos ni guiones")
        String documento,

        @NotBlank(message = "La nacionalidad es obligatoria")
        @Size(min = 2, max = 60, message = "La nacionalidad debe tener entre 2 y 60 caracteres")
        String nacionalidad,
@NotNull(message = "se debe especificar el genero")
        @Pattern(regexp = "^[F|M|X]$", message = "El género debe ser F, M o X")
        String genero,

        @NotBlank(message = "La escolaridad es obligatoria")
        @Size(max = 100, message = "La escolaridad no puede superar los 100 caracteres")
        String escolaridad
) {

public candidatoDto(Candidato x){
    this(
            x.getId(),
            x.getNombre(),
            x.getApellido(),
            x.getFecha(),
            x.getMail(),
           tipoDocumento.desdeTipo(x.getTipo()),
            x.getDocumento(),
            x.getNacionalidad(),
            x.getGenero().getDescripcion(),
            x.getEscolaridad()
    );
}

        @Override
        public String toString() {
                return "candidatoDtoAuth{" +
                        "id=" + id +
                        ", nombre='" + nombre + '\'' +
                        ", apellido='" + apellido + '\'' +
                        ", fecha=" + fecha +
                        ", mail='" + mail + '\'' +
                        ", tipoDocumento=" + tipo +
                        ", documento='" + documento + '\'' +
                        ", nacionalidad='" + nacionalidad + '\'' +
                        ", genero=" + genero +
                        ", escolaridad='" + escolaridad + '\'' +
                        '}';
        }
}