package com.practical.consultora_rrhh.dao;

import com.practical.consultora_rrhh.dto.response.opcionDto;
import com.practical.consultora_rrhh.interfaces.dao.IOpcionRespuestaDao;
import com.practical.consultora_rrhh.modelo.clases.Opcion;
import com.practical.consultora_rrhh.modelo.clases.OpcionRespuesta;
import com.practical.consultora_rrhh.interfaces.repositorio.opcionRespuestaRepository;
import com.practical.consultora_rrhh.dao.specifications.TextoSpecifications;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 Implementación de la interfaz DAO para operaciones CRUD y consultas
 * personalizadas sobre la entidad {@link OpcionRespuesta}.
 *No solo gestiona esta entidad sino tambien {@link Opcion}
 * Una opcion no puede existir sin una opcion de respuesta asociada
 * <p>Se abstrae el acceso a datos mediante
 * <a href="https://spring.io/projects/spring-data-jpa#overview">
 * Spring Data JPA</a>, delegando las operaciones de persistencia
 * en su correspondiente Repository.</p>
 *
  * @author  Besigniano Agustin
 * @since 1.0
 */
@Repository
public class OpcionRespuestaDaoImpl implements IOpcionRespuestaDao {

    private final opcionRespuestaRepository repository;

    public OpcionRespuestaDaoImpl(opcionRespuestaRepository repository) {
        this.repository = repository;
    }

    @Override
    public OpcionRespuesta guardar(OpcionRespuesta opcionRespuesta) {
        return repository.save(opcionRespuesta);
    }

  /**
*  Método especializado de {@link IOpcionRespuestaDao} que permite gestionar
* la modificación de los atributos habilitados para el usuario.
*
* <p>La actualización se limita exclusivamente a los atributos cuya
* modificación está permitida por las reglas del dominio, preservando
* aquellos que no pueden ser alterados.</p>
*/
    @Override
    @Transactional
        public OpcionRespuesta actualizar(String descripcion, OpcionRespuesta actual,List<opcionDto> opciones) {

        if(descripcion!=null){
        actual.setDescripcion(descripcion);}

        if (!opciones.isEmpty()) {
            actual.setOpciones(
                    opciones.stream()
                            .map(Opcion::new)
                            .toList());
        }
          return actual;}

    public List<OpcionRespuesta> obtenerTodos() {
        return repository.findAll();
    }

    /**
     * ELiminacion logica de La entidad
     */
    @Transactional
    public void eliminar(OpcionRespuesta opcionRespuesta) {
   opcionRespuesta.setEliminacion(true);}
    @Transactional
    public void reactivar(OpcionRespuesta opcionRespuesta) {
        opcionRespuesta.setEliminacion(false);
    }
    /**
     * Busca registros por coincidencia parcial
     */

    @Override
    public List<OpcionRespuesta> obtenerPorNombreParcial(String texto) {
     Specification<OpcionRespuesta> filtro=TextoSpecifications.coincidencia("nombre",texto,true,false);
     return  repository.findAll(filtro);

    }
    /**
     * Busca registros sin distinguir entre  mayúsculas y minúsculas (case-insensitive).
     */
    public boolean existePorNombreInsensible(String nombre) {
        Specification<OpcionRespuesta> filtro=TextoSpecifications.coincidencia("nombre",nombre,false,true);
        return  repository.exists(filtro);

    }
    public Optional<OpcionRespuesta> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public boolean existePorId(Long id) {
    return     repository.existsById(id);
    }
}
