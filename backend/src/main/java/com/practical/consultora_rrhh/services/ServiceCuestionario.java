package com.practical.consultora_rrhh.services;

import com.practical.consultora_rrhh.dto.request.candidatoDtoAuth;
import com.practical.consultora_rrhh.dto.request.cuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.request.itemCuestionarioDtoCreate;
import com.practical.consultora_rrhh.dto.response.cuestionarioDto;
import com.practical.consultora_rrhh.dto.response.evaluadosDto;
import com.practical.consultora_rrhh.excepciones.especializadas.atributos.CuestionarioActivoException;
import com.practical.consultora_rrhh.excepciones.genericas.ColeccionNotFoundException;
import com.practical.consultora_rrhh.excepciones.genericas.CuestionarioException;
import com.practical.consultora_rrhh.excepciones.genericas.ErrorExportException;
import com.practical.consultora_rrhh.excepciones.genericas.IntegridadComprometidaException;
import com.practical.consultora_rrhh.interfaces.dao.IcuestionarioDao;
import com.practical.consultora_rrhh.interfaces.dao.IitemCuestionarioDao;
import com.practical.consultora_rrhh.interfaces.services.IserviceCuestionario;
import com.practical.consultora_rrhh.modelo.clases.*;
import com.practical.consultora_rrhh.modelo.enums.tipoEstado;
import com.practical.consultora_rrhh.modelo.id.itemCuestionarioId;
import com.practical.consultora_rrhh.modelo.propiedades.CuestionarioProperties;
import com.practical.consultora_rrhh.services.mediator.*;
import com.practical.consultora_rrhh.validaciones.CuestionarioValidators;
import com.practical.consultora_rrhh.validaciones.internas.ItemCuestionarioValidators;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio que evalua candidatos para un puesto
 * Recupera cuestionarios para consultar
 * permite que el candidato complete el cuestionario
 * calcular el puntaje de cuestionarios finalizados
 * @author  Besigniano Agustin
 * @since 1.0
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
private  final OrdenMeritoCuestionarioMediator ordenMeritoMediator;
//--------------------------------------------------------------------------
public ServiceCuestionario(IcuestionarioDao dao, IitemCuestionarioDao item,
                           PuestoCuestionarioMediator puestoMediator, PuestoCuestionarioMediator puestoMediator1,
                           CandidatoCuestionarioMediator candidatoMediator,
                           ClaveGeneratorService generador, CuestionarioPreguntaMediator pregunta, CuestionarioValidators validators, CuestionarioProperties propiedadesCuestionario, ServiceExportacionEvaluados serviceExportacionEvaluados, OrdenMeritoCuestionarioMediator ordenMeritoMediator) {
    this.dao = dao;
    this.itemDao = item;
    this.puestoMediator = puestoMediator1;
    this.candidatoMediator = candidatoMediator;
    this.generador = generador;
    this.preguntaMediator=pregunta;
    this.validators = validators;
    this.propiedadesCuestionario = propiedadesCuestionario;
    this.serviceExportacionEvaluados = serviceExportacionEvaluados;
    this.ordenMeritoMediator = ordenMeritoMediator;
}
//--------------------------------------------------------------------------
@PostConstruct
public void init(){
    puestoMediator.registrarServicioB(this);
generador.inicializar(this.obtenerClaves());
candidatoMediator.registrarServicioA(this);
preguntaMediator.registrarServicioB(this);
ordenMeritoMediator.registrarServicioB(this);
}
  //---------------------------------LISTAR---------------------------------
//--------------------------------------------------------------------------
      public List<cuestionarioDto> obtenerCuestionarios(){
        return dao.obtenerTodos().stream().map(o->
                new cuestionarioDto(construirBloques(o),o)).toList();}
//---------------------------EVALUAR CANDIDATOS CU25-----------------------------------------------
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

        System.out.println("cantidad de preguntas : " +preguntas.size());

        preguntas=validators.validarPreguntas(competencias,preguntas);


short bloques=cantidadBloques(preguntas);
short accesos=propiedadesCuestionario.getCantidadDeAccesos();
short preguntasxBloque=propiedadesCuestionario.getPreguntasPorBloque();

        System.out.println("Bloques: "+bloques);
        System.out.println("cantidad de preguntas : " +preguntas.size());

 for(Candidato c:lista){
  List<Pregunta>  generadas=validators.generarPreguntas(competencias,preguntas);
    List<ItemCuestionario> items= ItemCuestionarioValidators.generar(bloques,generadas,preguntasxBloque);
     System.out.println("cantidad de preguntas : " +items.size());
     String clave=generador.generarClaveUnica();
Cuestionario nuevo=Cuestionario.GenerarCuestioario(p,c,items,bloques,clave,accesos);
//la cantidad de bloques lo determina la cantidad de factores corregir!!!!
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
//------------------------------------------------------------------------
//------------------------------CARGAR CLAVES GENERADAS------------------------------------------------------------
   @Override
   public List<String> obtenerClaves() {  return dao.obtenerClaves(); }
//--------------------------Exportar candidatos evaluados
        public byte[] exportar(List<evaluadosDto> lista) throws ErrorExportException {
        return serviceExportacionEvaluados.exportar(lista);
    }
//----------------------INTENTO DE CUESTIONARIO DEL USUARIO---------------------------------------------
@Override
@Transactional
public cuestionarioDto actualizarCuestionario(cuestionarioDtoCreate c, Long id) {
    Cuestionario actual = obtenerEntidad(id);
    Long idCandidato = c.id_candidato();
    Long idPuesto = c.id_puesto();
    List<itemCuestionarioDtoCreate> items = c.items();
    if (actual.getCandidato().getId().equals(idCandidato) && actual.getPuesto().getId().equals(idPuesto)) {

        actual.primerIngreso(propiedadesCuestionario.getCantidadDeAccesos(),propiedadesCuestionario.getTiempoMaximoActivo(),propiedadesCuestionario.getTiempoMaximoRealizacion());
        ItemCuestionarioValidators.validarItems(actual, items);

        List<Bloque> bloque = construirBloques(actual);

        List<Bloque> copia = ItemCuestionarioValidators.actualizarItems(bloque, items);
        // List<ItemCuestionario> modificados=new ArrayList<>();

         actualizar(bloque,copia);
            Long completados = bloque.stream()
                    .filter(Bloque::isCompletado)
                    .count();

            if (completados == bloque.size()) {actual.setEstado(tipoEstado.COMPLETADO);}
            actual.setFechaIntento(LocalDateTime.now());
            Cuestionario actualizado = dao.guardar(actual);


            cuestionarioDto nuevo=new cuestionarioDto(bloque,actualizado);

            return nuevo;
        }

        throw new IntegridadComprometidaException("El cuestionario recibido no pertenece al usuario");
    }



    //------------------------------------------------------------------------------
    @Override
    public Optional<Cuestionario> obtenerPorClave(String clave) {
        return Optional.empty();
    }

    @Override
    public Optional<Cuestionario> obtenerPorClave(candidatoDtoAuth dto) {
  return   dao.obtenerPorClave(dto);}

    //------------para CONSULTAS SOLAMENTE---------------------
    @Override
    public Cuestionario obtenerEntidad(Long id) {
        Optional<Cuestionario> r= dao.obtenerPorId(id);
        if(r.isPresent()){            return r.get();}
        throw new CuestionarioException("No se encontro el cuestionario",HttpStatus.NOT_FOUND);
    }
//-----------------------------------------OBTENER CUESTIONARIO CANDIDATO-----------------------------------------
@Transactional
public cuestionarioDto obtenerCuestionarioDto(candidatoDtoAuth candidatoDtoAuth) {

   Optional<Cuestionario> actual = obtenerPorClave(candidatoDtoAuth);
if(actual.isPresent()){
    Cuestionario c=actual.get();

    c.primerIngreso(propiedadesCuestionario.getCantidadDeAccesos(),
propiedadesCuestionario.getTiempoMaximoActivo(),propiedadesCuestionario.getTiempoMaximoRealizacion());

    List<Bloque> bloques = construirBloques(c);
tipoEstado estado=c.getEstado();
     switch (estado) {
        case EN_PROCESO -> {
             return new cuestionarioDto(bloques, c);
         }

        case COMPLETADO -> throw new CuestionarioException(
                "El cuestionario ya fue completado",
                HttpStatus.CONFLICT
        );

        case INCOMPLETO -> throw new CuestionarioException(
                "El tiempo para completar el cuestionario fue excedido",
                HttpStatus.GONE
        );

        case SIN_CONTESTAR -> throw new CuestionarioException(
                "Se superó el tiempo máximo para un cuestionario activo",
                HttpStatus.GONE
        );
    };
}
throw new CuestionarioException("No se encontró el cuestionario", HttpStatus.NOT_FOUND);
    }


    //--------------------------------------------------------------------------
    private List<PuestoCompetencia> obtenerPorPuesto(Long idPuesto){
        return  puestoMediator.obtenerPorPuesto(idPuesto);}


    public short cantidadBloques(List<Pregunta> pregunta){

        int cantidadFactores=pregunta.stream()
                .map(Pregunta::getFactor)
                .collect(Collectors.toSet())
                .size();
int configuracion=(int)(propiedadesCuestionario.getPreguntasPorBloque());

int cantidad=cantidadFactores*2;
int extra=cantidad%configuracion;

short bloques=(short)(cantidad/configuracion);
if(extra==0){return bloques;}
        return (short) (bloques + 1);
    }

    /**
     * Generamos la estructura auxiliar de bloques
     * @param c entidad de cuestionario
     * @return devolvemos una lista con la cantidad de bloques del cuestionario
     */
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

    /**
     * Actualizamos bloque con la copia al ser una copia profunda evitamos
     * actualizar entidades antes de tiempo al estar en un contexto transaccional
     * @param bloque bloque generado al recuperar el cuestionario
     * @param copia bloque actualizado con los items completados
     */
    private void actualizar(List<Bloque> bloque,List<Bloque> copia) {
        for (Bloque b : copia) {
            Short nrobloque = b.getNumeroBloque();
            boolean aux = bloque.stream()
                    .filter(o -> o.getNumeroBloque() == nrobloque)
                    .anyMatch(Bloque::isCompletado);

            if (!aux) {
                if (b.isCompletado()) {
                    bloque.stream()
                            .filter(o -> o.getNumeroBloque() == nrobloque)
                            .forEach(o -> {
                                o.setCompletado(true);
                                actualizarItems(o.getItemCuestionario(), b.getItemCuestionario());
                            });
                }
            }
        }
    }

    /**
     * Actualizamos cada item con su opcion elegida y obtenemos su puntaje
     * @param actual lista de items del bloque recuperado del cuestionario sin alterar
     * @param actualizado lista de items actualizadas del bloque copia
     */
    private void actualizarItems(List<ItemCuestionario> actual,List<ItemCuestionario> actualizado) {

        for (ItemCuestionario item : actual) {
            itemCuestionarioId id = item.getItem();
            Optional<ItemCuestionario> x = actualizado.stream()
                    .filter(o -> o.getItem().equals(id))
                    .findFirst();
            if (x.isPresent()) {
                ItemCuestionario nuevo = x.get();
                item.setOpcion(nuevo.getOpcion());
                item.setPuntaje(nuevo.getPuntaje());
            }}
    }
@Transactional
    private void CalcularPuntaje(Long idCuestionario) {
    Cuestionario c = obtenerEntidad(idCuestionario);
    if (c.getEstado().equals(tipoEstado.COMPLETADO)) {
        Map<Factor, List<ItemCuestionario>> factores = obtenerPorFactor(c);

        Map<Factor, BigDecimal> puntajePorfactor = puntajePorFactor(factores);
        Map<Competencia, List<Factor>> competencias = obtenerPorCompetencia(c);
        Map<Competencia, BigDecimal> puntajePorcompetencia = puntajePorCompetencia(competencias, puntajePorfactor);
    c.setPuntajes(puntajePorcompetencia);

    }
throw new CuestionarioActivoException("El cuestionario aun no ha sido completado");
}
    private Map<Factor,BigDecimal> puntajePorFactor(Map<Factor,List<ItemCuestionario>> factores) {
        Map <Factor,BigDecimal> puntajePorfactor = new HashMap<>();
       for (Factor f : factores.keySet()) {
           int puntaje = factores.get(f)
                   .stream()
                   .mapToInt(ItemCuestionario::getPuntaje)
                   .sum();
           BigDecimal puntos = BigDecimal.valueOf(puntaje / 2);
           puntajePorfactor.put(f, puntos);
       }
       return puntajePorfactor;
   }
   private Map<Competencia,BigDecimal> puntajePorCompetencia(Map<Competencia,List<Factor>> competencias,Map<Factor,BigDecimal> puntajePorfactor) {
       Map<Competencia,BigDecimal> puntajePorcompetencia = new HashMap<>();
        for (Competencia ls : competencias.keySet()) {
           List<Factor> lfc = competencias.get(ls);
           int cantidad = lfc.size();
            BigDecimal puntaje = BigDecimal.ZERO;
           for (Factor f : lfc) {
               BigDecimal fp = puntajePorfactor.get(f);
               if (fp != null) {
                   puntaje = puntaje.add(fp);
               }
           }
            BigDecimal resultado = puntaje.divide(
                    BigDecimal.valueOf(cantidad),
                    2,
                    RoundingMode.HALF_UP
            );
           puntajePorcompetencia.put(ls, resultado);
       }
return puntajePorcompetencia;   }




//-----------------------------------------------------------------------------------------------
    private Map<Factor, List<ItemCuestionario>> obtenerPorFactor(Cuestionario c) {
        return c.getItems().stream()
                .collect(Collectors.groupingBy(
                        o -> o.getPregunta().getFactor()
                ));}
    private Map<Competencia, List<Factor>> obtenerPorCompetencia(Cuestionario c) {
        return c.getItems().stream()
                .map(o -> o.getPregunta().getFactor())
                .distinct()
                .collect(Collectors.groupingBy(Factor::getCompetencia));
    }
    private Map<Competencia,Short> obtenerPonderaciones(Long puesto,List<Competencia> competencias) {
        Map<Competencia,Short> puntajePorcompetencia = new HashMap<>();
        List<PuestoCompetencia> pc=obtenerPorPuesto(puesto);
        for(Competencia ls : competencias) {
            PuestoCompetencia aux=pc.stream()
                    .filter(o->o.getCompetencia().equals(ls))
                    .findFirst().orElse(null);

            if(aux!=null) {
                puntajePorcompetencia.put(ls, aux.getPonderacion());
            }
        }
        return puntajePorcompetencia;
    }

    @Override
    public List<Cuestionario> obtenerPorPuestos(List<Long> puestos) {
List<Cuestionario> resultados=dao.buscarPorPuesto(puestos);
   if(resultados.isEmpty()) {
       throw  new ColeccionNotFoundException("No se encontraron cuestionarios para el puesto");
   }
   return resultados;

    }

    @Override
    public List<Cuestionario> listarPorPuesto(Long id) {
        List<Cuestionario> resultados= dao.buscarXPuesto(id);
        if(resultados.isEmpty()) {
            throw new ColeccionNotFoundException("No se encontraron cuestionarios para el puesto");
        }
        return resultados;
    }

    @Override
    public List<Cuestionario> obtenerxIdsCuestionario(List<Long> cuestionarios) {
 List<Cuestionario> lc=dao.buscarxIds(cuestionarios);
 if(lc.isEmpty()) {
     throw new  ColeccionNotFoundException("No se encontraron cuestionarios");}
 return lc;
    }
}





