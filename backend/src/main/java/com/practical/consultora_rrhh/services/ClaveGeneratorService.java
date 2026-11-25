package com.practical.consultora_rrhh.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class ClaveGeneratorService {

    private static final String ALFABETO = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LONGITUD = 8;
    private static final int MAX_INTENTOS = 12;

    private final SecureRandom random = new SecureRandom();
    // Set thread-safe para almacenar las claves existentes en memoria
    private final Set<String> clavesExistentes = ConcurrentHashMap.newKeySet();

    /**
     * Se llama al iniciar el sistema pasándole las claves que ya están en la BD.
     */
    public void inicializar(List<String> clavesDesdeBD) {
        if (clavesDesdeBD != null) {
            clavesExistentes.addAll(clavesDesdeBD);
        }
    }

    /**
     * Genera una clave única de 8 caracteres alfanuméricos, la registra y la devuelve.
     */
    public String generarClaveUnica() {
        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            String candidata = crearAleatoria();

            // add() devuelve true solo si la clave NO estaba en el Set (operación atómica)
            if (clavesExistentes.add(candidata)) {
                return candidata;
            }
        }

        throw new IllegalStateException(
                "No se pudo generar una clave única tras " + MAX_INTENTOS + " intentos."
        );
    }

    /**
     * Por si se elimina una entidad y quieres liberar su clave en memoria.
     */
    public void liberarClave(String clave) {
        if (clave != null) {
            clavesExistentes.remove(clave);
        }
    }

    private String crearAleatoria() {
        StringBuilder sb = new StringBuilder(LONGITUD);
        for (int i = 0; i < LONGITUD; i++) {
            sb.append(ALFABETO.charAt(random.nextInt(ALFABETO.length())));
        }
        return sb.toString();
    }
}
