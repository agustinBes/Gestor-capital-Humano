package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.dto.request.opcionDtoCreate;
import com.practical.consultora_rrhh.dto.response.opcionDto;
import com.practical.consultora_rrhh.modelo.id.OpcionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 *  Representa una posible opcion de respuesta para una pregunta
 *
 *
 * <p>Dado que el sistema utiliza cuestionarios de selección múltiple,
 * una misma opción puede estar asociada a distintas preguntas.
 * Esto permite reutilizar opciones de respuesta genéricas, como
 * "Sí", "No" o "Tal vez", almacenándolas una única vez en la base
 * de datos.</p>
 * @author Besigniano Agustin
 * @since 1.0
 *@version 1.0
 *     Analizar si se requieren métodos específicos para modificar
 *         sus atributos, en lugar de utilizar setters directamente.
 *
 */
@Entity
@Table(name = "opcion")
@Getter
@Setter
@NoArgsConstructor
public class Opcion {

    @EmbeddedId
    private OpcionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idOpcionRespuesta")
    @JoinColumn(name = "id_opcion_respuesta", nullable = false)
    private OpcionRespuesta opcionRespuesta;

    @Column(name = "orden", nullable = false)
    private short orden;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

//public  Opcion(opcionDtoCreate x){
//    id.getFila()=x.fila();
//    orden=x.orden();
//    descripcion=x.descripcion();
//
//}
//    public  Opcion(opcionDto x){
//    id.getFila()=x.fila();
//        orden=x.orden();
//        descripcion=x.descripcion();
//
//    }
public Opcion(opcionDtoCreate x) {
    this.id = new OpcionId();
    this.id.setFila((short) x.fila());
    this.orden = (short) x.orden();
    this.descripcion = x.descripcion();
}

    public Opcion(opcionDto x) {
        this.id = new OpcionId();
        this.id.setFila((short) x.fila());
        this.orden = (short) x.orden();
        this.descripcion = x.descripcion();
    }

    public Short getFila() {
    return this.id.getFila();
    }
}
