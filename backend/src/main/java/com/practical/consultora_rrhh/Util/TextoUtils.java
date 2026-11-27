package com.practical.consultora_rrhh.Util;

import com.practical.consultora_rrhh.excepciones.genericas.AtributoInvalidoException;

/**
 * Clases con algunas funciones usadas frecuentemente por la mayoria de los servicios que no requiere instanciar
 * @author Besigniano Agustin
 * @since 1.0
 */
public final class TextoUtils {
//evita la instanciacion de la clase
    private TextoUtils() {}

    /**
     * Eliminamos espacios en blanco al inicio, al final o entre palabras si hay mas de un espacio
     * Estos errores pueden marcar la diferencia entre encontrar los recursos que se necesitan o no
     * un espacio de mas que no devuelve resultados
     */
    public static String normalizar(String texto) {
        if (texto == null) return null;
//Remover casos donde haya mas de un espacion en blanco
        return texto.trim().replaceAll("\\s+", " ");}

    /**
     * comparamos 2 cadenas normalizadas ignorando mayusculas y minusculas
     */
    public static boolean comparar(String texto1, String texto2) {
        if (texto1 == null || texto2 == null) return false;
        return normalizar(texto1).equalsIgnoreCase(normalizar(texto2));}

    /**
     * Evaluamos que la cadena recibida contenga algo
     */
    public static boolean tieneContenido(String valor) {return valor != null && !valor.isBlank();}

    /**
     * Funcion para validar los id que se reciben en las peticiones como parametros sueltos
     * Recibirlas como Long o integer puede causar excepciones en el programa
     */
    public static Long identificador(String valor) {
        try {
            Long aux = Long.valueOf(valor.trim());
            if (aux <= 0) {
                throw new AtributoInvalidoException("El identificador recibido ('" + valor.toString() + "') no es válido. Debe ser un número entero mayor a 0.");}

            return aux;
        } catch (NumberFormatException e) {
            throw new AtributoInvalidoException("El identificador recibido ('" + valor.toString() + "') no es válido. Debe ser un número entero.");}
    }


}
