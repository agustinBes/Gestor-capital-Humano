package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.HistorialArchivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface archivoRepository extends JpaRepository<HistorialArchivo,Long> {
}
