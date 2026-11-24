package com.practical.consultora_rrhh.excepciones.genericas;

public class ColeccionNotFoundException extends EntityNotFoundException {
    public ColeccionNotFoundException( ) {
        super("No se encontraron resultados de busqueda");
    }
}
