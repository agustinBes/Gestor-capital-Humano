package com.practical.consultora_rrhh.modelo.clases;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Representa una Puesto de trabajo al que van a aplicar los candidatos
 *
  * @author Besigniano Agustin
 * @since 1.0
 *@version 1.0
*       Analizar si se requieren métodos específicos para modificar
 *         sus atributos, en lugar de utilizar setters directamente.
 */
@Getter
@Setter
@NoArgsConstructor // Genera el constructor vacío
@AllArgsConstructor
@Builder
@Entity
@Table(name="puesto")
public class Puesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String codigo;
@Column()
private String nombre;
@Column()
private String descripcion;
@Column()
private String empresa;
@Column()
    private boolean eliminacion;

}
