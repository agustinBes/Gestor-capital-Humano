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
private String registroEliminado;
@Column(name = "id_entidad")
private Long idEntidad;
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "tipo_auditoria", columnDefinition = "tipo_auditoria")
private tipoAuditoria auditoria;
@Column(name = "registro_actividad")
private LocalDateTime fecha;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(referencedColumnName = "id",name = "id_consultor")
private Consultor responsible;



public Auditoria(auditoriaDtoCreate dto,Consultor c){
    id=null;
    idEntidad=dto.id_entidad();
    registroEliminado=dto.registro();
    auditoria=dto.tipoAuditoria();
    fecha=dto.fecha();
    responsible =c;
}


}
