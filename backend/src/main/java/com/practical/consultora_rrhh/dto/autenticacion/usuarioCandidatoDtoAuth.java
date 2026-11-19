package com.practical.consultora_rrhh.dto.autenticacion;

import com.practical.consultora_rrhh.modelo.enums.tipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record usuarioCandidatoDtoAuth(
        @NotBlank(message = "se requiere elegir un tipo de documento")
        tipoDocumento tipo,
        @NotBlank(message = "El documento es obligatorio")
        @Size(max = 11,message = "Asegurate de no agregar ./- ni espacios en blanco")
        String documento,
        @NotBlank(message = "La clave es obligatorio")
        @Size(max = 20,message = "Longitud de contraseña excedida")
        String clave

) {
}
