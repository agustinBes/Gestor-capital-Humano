package com.practical.consultora_rrhh.modelo.enums;

public enum tipoAuditoria {
    FACTOR,
    PUESTO,
    COMPETENCIA,
    PREGUNTA,
    OPCION_RESPUESTA;

    public static tipoAuditoria desdeString(String valor) {
        return tipoAuditoria.valueOf(valor.toUpperCase());
    }
    public static String desdeTipo(tipoAuditoria x) {
        return x.name();
    }


}