package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.modelo.enums.tipoDocumento;
import com.practical.consultora_rrhh.modelo.enums.tipoGenero;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "candidato")
public class Candidato {
    @Id
    @Column(name = "nro_candidato")
    private Long id;
    @Column(name="fecha_nacimiento")
private LocalDate fecha;
    private String nombre;
    private String apellido;
    private String mail;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo_documento", columnDefinition = "tipo_documento")
    private tipoDocumento tipo;
    private String documento;
    private String nacionalidad;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "genero", columnDefinition = "genero")
    private tipoGenero genero;
    private String escolaridad;
    private boolean eliminacion;


public Candidato(candidatoDto x){
    id=x.id();
    fecha=x.fecha();
    nombre=x.nombre();
    apellido=x.apellido();
    mail=x.mail();
    tipo=tipoDocumento.desdeString(x.tipo());
    documento=x.documento();
    nacionalidad=x.nacionalidad();
   genero=tipoGenero.valueOf(x.genero());
    escolaridad=x.escolaridad();
    eliminacion=false;
}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return Objects.equals(id, candidato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
