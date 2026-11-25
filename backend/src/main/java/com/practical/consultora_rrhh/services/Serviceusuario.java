package com.practical.consultora_rrhh.services;

//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import com.practical.consultora_rrhh.interfaces.auxiliares.Iconsultor;
import com.practical.consultora_rrhh.interfaces.repositorio.consultorRepository;
import com.practical.consultora_rrhh.modelo.clases.Consultor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Serviceusuario implements Iconsultor {
    private final consultorRepository consultor;


    public Serviceusuario(consultorRepository consultor) {
              this.consultor = consultor;

    }


    @Override
    public Consultor obtenerConsultor(Long id) {
        return consultor.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe el consultor en el sistema"));
    }

}