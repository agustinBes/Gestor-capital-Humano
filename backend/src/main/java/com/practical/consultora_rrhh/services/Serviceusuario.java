package com.practical.consultora_rrhh.services;

//import com.nimbusds.jose.JWSAlgorithm;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
import com.practical.consultora_rrhh.Util.TextoUtils;
import com.practical.consultora_rrhh.dto.autenticacion.usuarioCandidatoDtoAuth;
import com.practical.consultora_rrhh.dto.autenticacion.usuarioConsultorDtoAuth;
import com.practical.consultora_rrhh.dto.autenticacion.usuarioDtoAuth;
import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import com.practical.consultora_rrhh.interfaces.auxiliares.auth.Icandidato;
import com.practical.consultora_rrhh.interfaces.auxiliares.auth.Iautenticador;
import com.practical.consultora_rrhh.interfaces.auxiliares.auth.Iconsultor;
import com.practical.consultora_rrhh.interfaces.repositorio.candidatoRepository;
import com.practical.consultora_rrhh.interfaces.repositorio.consultorRepository;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import com.practical.consultora_rrhh.modelo.clases.Consultor;
import com.practical.consultora_rrhh.modelo.enums.tipoDocumento;
import com.practical.consultora_rrhh.modelo.enums.tipoGenero;
import jakarta.transaction.Transactional;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Serviceusuario implements Iautenticador, Icandidato, Iconsultor {
private final consultorRepository consultor;
private final candidatoRepository candidato;
//private final PasswordEncoder encoder;
//private final byte[] secreto;

private Candidato aux2=new Candidato();
public Serviceusuario(consultorRepository consultor, candidatoRepository candidato){
                      //PasswordEncoder encoder,
                     // @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}") String secretKey) {
    this.consultor = consultor;
    this.candidato = candidato;
//    this.encoder = encoder;
 //   this.secreto=secretKey.getBytes(StandardCharsets.UTF_8);
}
    @Override
    public usuarioDtoAuth autenticarConsultor(usuarioConsultorDtoAuth dto) {
/*
    Consultor c=obtenerConsultor(dto.nombreUsuario());
  boolean autenticacion=  encoder.matches(dto.contraseña(),c.getClave());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "holamundo1234";
        String hash = encoder.encode(raw);

        System.out.println("Hash generado: " + hash);
        System.out.println("Coincide: " + encoder.matches(raw, hash)); // debe imprimir true


if(!autenticacion){
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
}
String token=generarToken(c.getUsuario());

usuarioDtoAuth aux=new usuarioDtoAuth(token,c.getNombre(),c.getId());
return aux;*/
        return null;
    }

    @Override
    public usuarioDtoAuth autenticarCandidato(usuarioCandidatoDtoAuth dto) {
        Candidato c = candidato.findByTipoAndDocumento(dto.tipo(), dto.documento())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el candidato con ese documento: " + dto.documento()
                ));
        //validar cuestionario
        return new usuarioDtoAuth(" ",c.getNombre(),c.getId());
    }

    @Override
    public List<candidatoDto> importarCandidatos(List<candidatoDto> candidato) {
        return List.of();
    }

    @Override
    public candidatoDto crearCandidato(candidatoDto dto) {
    Candidato nuevo=Candidato.builder()
            .id(dto.id())
            .nombre(dto.nombre())
            .fecha(dto.fecha())
            .mail(dto.mail())
            .tipo(tipoDocumento.desdeString(dto.tipo()))
            .genero(tipoGenero.valueOf(dto.genero()))
            .documento(dto.documento())
            .nacionalidad(dto.nacionalidad())
            .escolaridad(dto.escolaridad())
            .build();
Candidato guardado=candidato.save(nuevo);
return new candidatoDto(guardado);
 }

    @Override
    @Transactional
    public Candidato actualizarCandidato(Long id, String clave) {

return null;
    }

    @Override
    @Transactional
    public void eliminarCandidato(Long id) {
        Candidato aux=obtenerCandidato(id);
candidato.delete(aux);
}

    @Override
    public Candidato obtenerCandidato(Long id) {
        return candidato.findById(id).orElseThrow(()-> new  EntityNotFoundException("No existe el candidato en el sistema"));
    }
    @Override
    public Consultor obtenerConsultor(String usuario){
    String aux= TextoUtils.normalizar(usuario).toUpperCase();
    return consultor.findByUsuario(aux).orElseThrow(()-> new EntityNotFoundException("Nombre de usuario incorrecto"));
}
@Override
    public Consultor obtenerConsultor(Long id){
    return  consultor.findById(id).orElseThrow(()-> new EntityNotFoundException("No existe el consultor en el sistema"));
    }


//    private String generarToken(String usuario) {
//        try {
//            Instant ahora = Instant.now();
//            JWTClaimsSet claims = new JWTClaimsSet.Builder()
//                    .subject(usuario)
//                    .claim("rol", "CONSULTOR")
//                    .issueTime(Date.from(ahora))
//                    .expirationTime(Date.from(ahora.plus(4, ChronoUnit.HOURS)))
//                    .build();
//
//            SignedJWT signedJWT = new SignedJWT(
//                    new JWSHeader(JWSAlgorithm.HS256),
//                    claims
//            );
//            signedJWT.sign(new MACSigner(this.secreto));
//            return signedJWT.serialize();
//        } catch (Exception ex) {
//            throw new IllegalStateException("Error al generar el JWT", ex);
//        }
//    }
}
