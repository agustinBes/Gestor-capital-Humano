package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dto.request.cuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;
import com.practical.consultora_rrhh.dto.response.evaluadosDto;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.ClaveInvalidaException;
import com.practical.consultora_rrhh.excepciones.genericas.CuestionarioException;
import com.practical.consultora_rrhh.excepciones.genericas.EntityNotFoundException;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import com.practical.consultora_rrhh.excepciones.genericas.IntegridadComprometidaException;
import com.practical.consultora_rrhh.interfaces.dao.IcuestionarioDao;
import com.practical.consultora_rrhh.interfaces.dao.IitemCuestionarioDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.modelo.clases.*;
import com.practical.consultora_rrhh.modelo.enums.tipoEstado;
import com.practical.consultora_rrhh.modelo.propiedades.CuestionarioProperties;
import com.practical.consultora_rrhh.services.mediator.CandidatoCuestionarioMediator;
import com.practical.consultora_rrhh.services.mediator.CuestionarioPreguntaMediator;
import com.practical.consultora_rrhh.services.mediator.PuestoCuestionarioMediator;
import com.practical.consultora_rrhh.services.mediator.ServiceExportacionEvaluados;
import com.practical.consultora_rrhh.validaciones.CuestionarioValidators;
import com.practical.consultora_rrhh.validaciones.internas.ItemCuestionarioValidators;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */

@Service
public class ServiceCuestionario implements IserviceCuestionario {
private final IcuestionarioDao dao;
private final IitemCuestionarioDao itemDao;
private final PuestoCuestionarioMediator puestoMediator;
private final CandidatoCuestionarioMediator candidatoMediator;
private final CuestionarioPreguntaMediator preguntaMediator;
private final ClaveGeneratorService generador;
private final CuestionarioValidators validators;
private final CuestionarioProperties propiedadesCuestionario;
private  final ServiceExportacionEvaluados serviceExportacionEvaluados;
//--------------------------------------------------------------------------
public ServiceCuestionario(IcuestionarioDao dao, IitemCuestionarioDao item,
                           PuestoCuestionarioMediator puestoMediator, PuestoCuestionarioMediator puestoMediator1,
                           CandidatoCuestionarioMediator candidatoMediator,
                           ClaveGeneratorService generador, CuestionarioPreguntaMediator pregunta, CuestionarioValidators validators, CuestionarioProperties propiedadesCuestionario, ServiceExportacionEvaluados serviceExportacionEvaluados) {
    this.dao = dao;
    this.itemDao = item;
    this.puestoMediator = puestoMediator1;
    this.candidatoMediator = candidatoMediator;
    this.generador = generador;
    this.preguntaMediator=pregunta;
    this.validators = validators;
    this.propiedadesCuestionario = propiedadesCuestionario;
    this.serviceExportacionEvaluados = serviceExportacionEvaluados;
}
//--------------------------------------------------------------------------
@PostConstruct
public void init(){
    puestoMediator.registrarServicioB(this);
generador.inicializar(this.obtenerClaves());
candidatoMediator.registrarServicioA(this);
preguntaMediator.registrarServicioB(this);
}
//--------------------------------------------------------------------------
    /**
     * Generador de formularios para un puesto y una lista de candidatos
     *
     * Proceso de validacion
     *
     * 1. Recuperar las competencia y en el proceso verificamos el puesto
     * 2. Validar y recuperar la totalidad de los candidatos
     * 3. validar que no tengan cuestionarios activos
     * 4. recuperar todas las preguntas asociadas a las competencias del puesto
     * 5. validar criterios de las competencias
     * @param idPuesto puesto a evaluar
     * @param candidato candidatos a evaluar
     * @return objeto record {@link evaluadosDto} que devuelve los candidatos y las claves
     */

    @Override
    public List<evaluadosDto> generarCuestionario(Long idPuesto, List<Long> candidato) {
    List<Cuestionario> generados = new ArrayList<>();
//-------------------Puestos y competencias---------------------------------------------------
    //Recuperar puesto y competencias
    List<PuestoCompetencia> listapc=obtenerPorPuesto(idPuesto);
Puesto p=listapc.getFirst().getPuesto();
List<Competencia> competencias=listapc.stream().map(PuestoCompetencia::getCompetencia).toList();
//---------------------Candidatos-----------------------------------------------------
List<Candidato> lista=candidatoMediator.obtenerCandidatos(candidato);
//----------------------Cuestionarios activos-------------------------------------------
validators.ValidarCuestionariosActivos(candidato);
//---------------------------Totalidad de preguntas para las competencias del puesto--------------------
 List<Pregunta> preguntas=preguntaMediator.obtenerPreguntasPorCompetencias(competencias);

        preguntas=validators.validarPreguntas(competencias,preguntas);


short bloques=cantidadBloques(preguntas.size());
short accesos=propiedadesCuestionario.getCantidadDeAccesos();
short preguntasxBloque=propiedadesCuestionario.getPreguntasPorBloque();

 for(Candidato c:lista){
  List<Pregunta>  generadas=validators.generarPreguntas(competencias,preguntas);
    List<ItemCuestionario> items= ItemCuestionarioValidators.generar(bloques,generadas,preguntasxBloque);
     String clave=generador.generarClaveUnica();
Cuestionario nuevo=Cuestionario.GenerarCuestioario(p,c,items,bloques,clave,accesos);

generados.add(nuevo);
 }

List<Cuestionario> guardados= dao.guardarTodos(generados);
        Map<Candidato, String> claves = guardados.stream()
                .collect(Collectors.toMap(
                        Cuestionario::getCandidato,
                        Cuestionario::getClave
                ));
 List<evaluadosDto> evaluados= new ArrayList<>();
 for(Candidato c:lista){
    evaluadosDto nuevo=new evaluadosDto(c,claves.get(c));
evaluados.add(nuevo);

    }
return evaluados;}

//--------------------------------------------------------------------------

//-------------------------------------------------------------------------------------
    private void actualizarCuestionariosVencidos(List<Cuestionario> activos){
        LocalDateTime ahora = LocalDateTime.now();

        List<Cuestionario> vencidos = activos.stream()
                .filter(c -> ahora.isAfter(
                        c.getFechaCreacion().plus(propiedadesCuestionario.getTiempoMaximoActivo())
                ))
                .toList();

        vencidos.forEach(c -> {
            c.setEstado(tipoEstado.SIN_CONTESTAR);
            c.setFechaIntento(ahora);
        });

    dao.guardarTodos(vencidos);

    }



    @Override
    public cuestionarioDto recuperarCuestionario(String clave, String documento){
   Optional<Cuestionario> buscado=dao.obtenerPorClave(clave);
   if(buscado.isPresent()){
       if(!documento.equals(buscado.get().getCandidato().getDocumento())){
       throw new ClaveInvalidaException(HttpStatus.FORBIDDEN,"Clave de acceso invalida");
       }
       else{}
                 }

   throw new ClaveInvalidaException(HttpStatus.NOT_FOUND,"credenciales invalidas");
   }
//------------------------------------------------------------------------------------------

    @Override
    public List<Cuestionario> recuperarCuestionarios(List<Long> candidato) {
        List<Cuestionario> cuestionarios=dao.cuestionariosActivos(candidato);
if(cuestionarios.isEmpty()){
    return null;}

else{
return     actualizarCuestionarios(cuestionarios);
}
    }




//estado internamente
    private List<Cuestionario> actualizarCuestionarios(List<Cuestionario> cuestionarios) {
        for(Cuestionario c:cuestionarios){

        }
        return new ArrayList<>(cuestionarios);

    }

    //------------------------------------------------------------------------
   @Override
   public List<String> obtenerClaves() {  return dao.obtenerClaves(); }

    public byte[] exportar(List<evaluadosDto> lista) throws ErrorExportException {
        return serviceExportacionEvaluados.exportar(lista);
    }
//-------------------------------------------------------------------
@Override
public cuestionarioDto actualizarCuestionario(cuestionarioDtoCreate c, Long id) {
   Cuestionario actual = obtenerEntidad(id);
Long idCandidato=c.id_candidato();
Long idPuesto=c.id_puesto();
List<itemCuestionarioDtoCreate> items=c.items();
if(actual.getCandidato().getId().equals(idCandidato) && actual.getPuesto().getId().equals(idPuesto)){

    ItemCuestionarioValidators.validarItems(actual,items);



    return  new cuestionarioDto();}

throw new IntegridadComprometidaException("El cuestionario recibido no pertenece al usuario");
    }






    //------------------------------------------------------------------------------
    @Override
    public Optional<Cuestionario> obtenerPorClave(String clave) {
        return Optional.empty();
    }

    @Override
    public Cuestionario obtenerEntidad(Long id) {
        Optional<Cuestionario> r= dao.obtenerPorId(id);

        if(r.isPresent()){
            return r.get();
        }
        throw new CuestionarioException("No se encontro el cuestionario",HttpStatus.NOT_FOUND);

    }

    //--------------------------------------------------------------------------
    private List<PuestoCompetencia> obtenerPorPuesto(Long idPuesto){
        return  puestoMediator.obtenerPorPuesto(idPuesto);}
    public short cantidadBloques(int cantidadPregunta){

        short preguntasPorBloque=propiedadesCuestionario.getPreguntasPorBloque();

        short cantidadPreguntas=(short) cantidadPregunta;
        short bloques=0;
        if(cantidadPreguntas<=preguntasPorBloque){
            bloques=1;}
        else{
            bloques= (short) (cantidadPreguntas /preguntasPorBloque);
            boolean resto=(cantidadPreguntas%preguntasPorBloque!=0);
            bloques = (short) (bloques +(short)(resto? 1:0));
        }
        return bloques;}

private List<Bloque> construirBloques(Cuestionario c){
        Long idCuestionario=c.getId();
        List<Bloque> bloques=new ArrayList<>();
        short cantidad=c.getNroBloques();

   for(int i=1;i<=cantidad;i++){
      short bloque=(short)i;
       List<ItemCuestionario> items=c.getItems().stream()
               .filter(o->o.getNroBloque()==bloque )
               .toList();
Bloque nuevo= Bloque.builder()
        .idCuestionario(idCuestionario)
        .completado(false)
        .numeroBloque(bloque)
        .itemCuestionario(items)
        .build();
nuevo.completado();
bloques.add(nuevo);}
        return bloques;

   }

}

