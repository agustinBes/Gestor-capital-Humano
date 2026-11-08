package com.practical.consultora_rrhh.modelo.clases;

import com.practical.consultora_rrhh.dto.request.auditoriaDtoCreate;
import com.practical.consultora_rrhh.dto.response.auditoriaDto;
import com.practical.consultora_rrhh.modelo.enums.tipoAuditoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Auditoria")
public class Auditoria {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Long id;
@Column(name = "nombre_registro")
private String RegistroEliminado;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo_auditoria", columnDefinition = "tipo_auditoria")
private tipoAuditoria auditoria;
@Column(name = "registro_actividad")
private LocalDateTime fecha;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(referencedColumnName = "id",name = "id_consultor")
private Consultor responsible;


public Auditoria(auditoriaDto dto){
    id=dto.id();
    RegistroEliminado=dto.registro();
    auditoria=tipoAuditoria.valueOf(dto.tipo());
    fecha=dto.fecha();
    responsible =new Consultor();
}
public Auditoria(auditoriaDtoCreate dto){
    id=null;
    RegistroEliminado=dto.registro();
    auditoria=dto.tipoAuditoria();
    fecha=dto.fecha();
    responsible =null;
}


}
