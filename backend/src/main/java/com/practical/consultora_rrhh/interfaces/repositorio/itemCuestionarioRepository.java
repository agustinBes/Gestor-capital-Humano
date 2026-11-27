package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.ItemCuestionario;
import com.practical.consultora_rrhh.modelo.id.itemCuestionarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Interfaz que hereda de JpaRepository  obteniendo un conjunto importante de consultas
 * con solo definir el metodo  JpaRepository se encarga de traducir la consulta a codigo sql
 * consultar la bdd y devolver los resultados en el tipo de la entidad.
  * @author Besigniano Agustin
 * @since 1.0
 */
@Repository
public interface itemCuestionarioRepository extends JpaRepository<ItemCuestionario, itemCuestionarioId> {
    List<ItemCuestionario> findAllByCuestionario_Id(Long idCuestionario);
}
