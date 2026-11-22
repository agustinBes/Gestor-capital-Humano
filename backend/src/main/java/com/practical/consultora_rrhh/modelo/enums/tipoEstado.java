package com.practical.consultora_rrhh.modelo.enums;

public enum tipoEstado {
    COMPLETADO,
    ACTIVO,
    INCOMPLETO,
    EN_PROCESO;

    public static tipoEstado desdeString(String valor) {
        return tipoEstado.valueOf(valor.toUpperCase());
    }
    public static String desdeTipo(tipoEstado x) {
        return x.name();
    }
    }