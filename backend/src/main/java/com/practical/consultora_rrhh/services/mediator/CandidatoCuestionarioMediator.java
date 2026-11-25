package com.practical.consultora_rrhh.services.mediator;

import com.practical.consultora_rrhh.dto.response.candidatoDto;
import com.practical.consultora_rrhh.interfaces.services.IserviceCandidato;
import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.modelo.clases.Candidato;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CandidatoCuestionarioMediator extends GenericMediator<IserviceCuestionario, IserviceCandidato> {

protected IserviceCandidato serviceCandidato(){
    return servicioB;
}
protected IserviceCuestionario serviceCuestionario(){
    return servicioA;
}


    public List<Candidato> obtenerCandidatos(List<Long> candidato) {

return  serviceCandidato().obtenerCandidatos(candidato);
    }
}
