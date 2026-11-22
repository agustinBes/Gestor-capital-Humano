package com.practical.consultora_rrhh.modelo.clases;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "consultor")
@NoArgsConstructor
@AllArgsConstructor
public class Consultor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String usuario;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
     private String clave;





}
