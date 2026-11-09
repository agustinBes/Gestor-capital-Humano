package com.practical.consultora_rrhh.Util;

public final class TextoUtils {

    private TextoUtils() {
        // Evita instanciar la clase
    }

    public static String normalizar(String texto) {
        if (texto == null) return null;
//Remover casos donde haya mas de un espacion en blanco
        return texto.trim().replaceAll("\\s+", " ");
    }

    public static boolean comparar(String texto1, String texto2) {
        if (texto1 == null || texto2 == null) return false;

        return normalizar(texto1).equalsIgnoreCase(normalizar(texto2));
    }
    public static boolean tieneContenido(String valor) {

        return valor != null && !valor.isBlank();
    }
}