package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.modelo.enums.tipoEstado;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import java.lang.invoke.CallSite;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "cuestionario")
public class Cuestionario {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_candidato", nullable = false)
    private Candidato candidato;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_puesto", nullable = false)
    private Puesto puesto;
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column()
    tipoEstado estado;
    @Column(name = "cantidad_bloques")
private short NroBloques=0;
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    @Column(name = "fecha_intento")
    private LocalDateTime fechaIntento;
    @Column
    private short puntaje=0;
    @Column
private String clave;
    @Column(name="nro_acceso")
    private short NroAccesos=4;
@OneToMany(mappedBy = "cuestionario", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ItemCuestionario> items;
}