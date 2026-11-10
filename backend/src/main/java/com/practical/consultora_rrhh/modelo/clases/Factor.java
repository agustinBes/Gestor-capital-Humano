package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.dto.response.factorDto;
import jakarta.persistence.*;
import lombok.*;

/**
*  Representa un factor o aspecto específico de una competencia
* requerida para un puesto de trabajo.
*
* <p>Dispone de un atributo de eliminación lógica que permite
* marcar el registro como eliminado sin borrarlo físicamente
* de la base de datos.</p>
*
* @author Besigniano Agustin
* @since 1.0
*@version 1.0
* <h3>Mejoras y cuestiones pendientes</h3>
* <ul>
*    <li>Analizar si se requieren métodos específicos para modificar
*         sus atributos, en lugar de utilizar setters directamente.</li>
*     <li>Determinar la finalidad del atributo {@code orden} y las
*         reglas de negocio asociadas.</li>
* </ul>
*/

@Getter
@Setter
@NoArgsConstructor // Genera el constructor vacío
@AllArgsConstructor
@Entity()
@Builder
@Table(name = "factor")
public class Factor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String codigo;
@ManyToOne()
@JoinColumn(name = "id_competencia", referencedColumnName = "id")
    private Competencia competencia;
@Column()
    private String nombre;
@Column ()
private String descripcion;
    @Column (name = "Nro_orden")
    private short orden;
@Column()
    private boolean eliminacion;


}
