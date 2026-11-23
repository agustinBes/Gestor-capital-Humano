package com.practical.consultora_rrhh.modelo.clases;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase para mantener un historial de los archivos de candidatos importados y el responsable de la migracion
 * @author  Besigniano Agustin
 * @since 1.0
 */
@Entity
@Table(name="archivo_importado")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistorialArchivo {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Long id;

private String nombre;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consultor", nullable = false)
    private Consultor consultor;
}
