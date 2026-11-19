package com.practical.consultora_rrhh.Util;

import com.practical.consultora_rrhh.excepciones.genericas.AtributoInvalidoException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public final class TextoUtils {

    private TextoUtils() {
        // Evita instanciar la clase
    }
   /**
     * Eliminamos espacios en blanco al inicio, al final o entre palabras si hay mas de un espacio
     */

    public static String normalizar(String texto) {
        if (texto == null) return null;
//Remover casos donde haya mas de un espacion en blanco
        return texto.trim().replaceAll("\\s+", " ");
    }
    /**
     * comparamos 2 cadenas normalizadas ignorando mayusculas y minusculas
     */
    public static boolean comparar(String texto1, String texto2) {
        if (texto1 == null || texto2 == null) return false;

        return normalizar(texto1).equalsIgnoreCase(normalizar(texto2));
    }
    public static boolean tieneContenido(String valor) {

        return valor != null && !valor.isBlank();
    }
    public static Long identificador(String valor) {
             try {
            Long aux = Long.valueOf(valor.trim());
            if (aux <= 0) {
                throw new AtributoInvalidoException("El identificador recibido ('" + valor.toString() + "') no es válido. Debe ser un número entero mayor a 0.");
            }

            return aux;

        } catch (NumberFormatException e) {
            throw new AtributoInvalidoException("El identificador recibido ('" + valor.toString() + "') no es válido. Debe ser un número entero.");
        }
    }}
