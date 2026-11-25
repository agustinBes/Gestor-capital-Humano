package com.practical.consultora_rrhh.excepciones.genericas;

import com.practical.consultora_rrhh.modelo.clases.Factor;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?>  entidad,String id) {
            super(construirMensaje(entidad, id));
        }
    public EntityNotFoundException(String message) {
        super(message);
    }

        private static String construirMensaje(Class<?> tipoEntidad, Object id) {
            String nombreEntidad = (tipoEntidad != null) ? tipoEntidad.getSimpleName() : "";

            String articuloYEntidad = switch (nombreEntidad) {
                case "Factor" -> "el factor";
                case "Competencia" -> "la competencia";
                case "Pregunta" -> "la pregunta";
                case "OpcionRespuesta" -> "la opción de respuesta";
                case "Candidato" -> "el candidato";
                case "Consultor" -> "el consultor";
                case "Puesto" -> "el puesto";
                               default -> "el registro";
            };

            return String.format("No se encontró %s con el id %s en el sistema", articuloYEntidad, id);
        }
    }