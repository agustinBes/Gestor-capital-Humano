package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;
import com.practical.consultora_rrhh.modelo.id.itemCuestionarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface itemCuestionarioRepository extends JpaRepository<ItemCuestionario, itemCuestionarioId> {
    List<ItemCuestionario> findAllByCuestionario_Id(Long idCuestionario);
}
