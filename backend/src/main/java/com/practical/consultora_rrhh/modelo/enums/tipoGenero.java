package com.practical.consultora_rrhh.modelo.enums;

public enum tipoGenero {
    F("Femenino"),
    M("Masculino"),
    X("No binario / Otro");

    private final String descripcion;

    tipoGenero(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}