package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.modelo.enums.tipoEstado;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
private short NroBloques;
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    @Column(name = "fecha_intento")
    private LocalDateTime fechaIntento;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @ElementCollection
    @CollectionTable(
            name = "puntaje_competencia",
            joinColumns = @JoinColumn(name = "id_cuestionario"))
    @MapKeyJoinColumn(name = "id_competencia")
    @Column(name = "puntaje_obtenido", nullable = false,precision = 3,scale = 2)
    private Map<Competencia, BigDecimal> puntajes = new HashMap<>();
    @Column
private String clave;
    @Column(name="nro_acceso")
    private short NroAccesos;
@OneToMany(mappedBy = "cuestionario", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ItemCuestionario> items;

public static Cuestionario GenerarCuestioario(Puesto puesto, Candidato candidato, List<ItemCuestionario> items, Short bloques, String clave, short NroAccesos) {
    Cuestionario nuevo= Cuestionario.builder()
            .id(null)
            .candidato(candidato)
            .estado(tipoEstado.ACTIVO)
            .fechaInicio(null)
            .fechaIntento(null)
            .fechaCreacion(LocalDateTime.now())
            .puesto(puesto)
            .NroAccesos(NroAccesos )
            .NroBloques( bloques)
            .clave(clave)
            .items(items)
            .build();
    items.forEach(item-> item.setCuestionario(nuevo));
    return nuevo;
}

public void validarEstado(Duration tiempoMaximoActivo,Duration tiempoMaximoRealizacion){

    LocalDateTime actual= LocalDateTime.now();
    switch (estado) {
        case ACTIVO -> {
            if (actual.isAfter(fechaCreacion.plus(tiempoMaximoActivo))) {
                estado = tipoEstado.SIN_CONTESTAR;
                fechaIntento = actual;
            }
                    }

        case EN_PROCESO -> {
            if (actual.isAfter(fechaInicio.plus(tiempoMaximoRealizacion))) {
                estado = tipoEstado.INCOMPLETO;
                fechaIntento = actual;
            }
        }

        default -> {
            // No requiere validación temporal.
        }
    }
}
//Solo para el candidato cuando ingresa por primera vez
public void primerIngreso(short cantidadAcceso,Duration tiempoMaximoActivo,Duration tiempoMaximoRealizacion){
    if(cantidadAcceso==NroAccesos){
                LocalDateTime ahora = LocalDateTime.now();

         superatiempoMaximoActivo(tiempoMaximoActivo);
         superaTiempoMaximoRealizacion(tiempoMaximoRealizacion);
         if(estado==tipoEstado.ACTIVO) {

             fechaInicio = ahora;
             NroAccesos = (short) (NroAccesos - 1);
             estado = tipoEstado.EN_PROCESO;
         }
    }
    else{
        this.validarEstado(tiempoMaximoActivo,tiempoMaximoRealizacion);
    }
}

public void superaTiempoMaximoRealizacion(Duration tiempo) {
    LocalDateTime actual= LocalDateTime.now();

    if (estado != tipoEstado.INCOMPLETO
            && estado != tipoEstado.COMPLETADO) {
    if(actual.isAfter(fechaCreacion.plus(tiempo))) {
        estado=tipoEstado.INCOMPLETO;
        fechaIntento = actual;
           }}

}
public void superatiempoMaximoActivo(Duration tiempo) {
    LocalDateTime ahora= LocalDateTime.now();
if(estado ==tipoEstado.ACTIVO) {
    if (ahora.isAfter(fechaCreacion.plus(tiempo))) {
        estado = tipoEstado.SIN_CONTESTAR;
        fechaIntento = ahora;
    }
}}

    public void completado(List<Bloque> bloques){

        long cantidadTotal = bloques.size();
        long cantidad = bloques.stream()
                .filter(Bloque::isCompletado)
                .count();
if(cantidad==cantidadTotal) {
    estado = tipoEstado.COMPLETADO;
    fechaIntento = LocalDateTime.now();

}



}



}

