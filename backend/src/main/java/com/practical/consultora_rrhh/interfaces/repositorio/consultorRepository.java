package com.practical.consultora_rrhh.interfaces.repositorio;

import com.practical.consultora_rrhh.modelo.clases.Consultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface consultorRepository extends JpaRepository<Consultor,Long> {

   boolean existsByUsuarioAndClave(String nombre,String clave);

   Optional<Consultor> findByUsuario(String texto);
}
