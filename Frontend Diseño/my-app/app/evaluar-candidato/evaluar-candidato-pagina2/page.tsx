"use client";
import { useState } from "react";
import { useEffect } from "react";
import Boton from "../../../componentes/boton";
import Tabla from "../../../componentes/tabla";
import Desplegable from "../../../componentes/desplegable";
import Link from "next/dist/client/link";

export default function EvaluarCandidatoPagina2() {
  const [funcion, setFuncion] = useState("");
  const [paddingArriba, setPaddingArriba] = useState("17rem");
  const opciones = [
    "Consultor",
    "Analista",
    "Desarrollador",
    "Gerente"
  ];
   
  useEffect(() => {const ajustarPadding = () => {if (window.innerWidth < 1400) {setPaddingArriba("1rem");
     } else {setPaddingArriba("9rem");  }};
    ajustarPadding(); 
     window.addEventListener("resize", ajustarPadding);
    return () => window.removeEventListener("resize", ajustarPadding);}, []);

    
  return (

    <div style={{
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "flex-start", 
  paddingTop: paddingArriba
  }}> 
    <div className="flex flex-col flex-1" style={{display: "flex",maxWidth: "800px", margin: "2rem auto", border: "2px solid #1E2A1C", borderRadius: "8px", background: "#F5F5F5", alignItems: "center",width: "100%",paddingBottom: "2rem"}}>
          <h2 style={{ display: "flex",margin: "1rem auto 0.5rem", fontSize: "2rem" }}>
          <img src="/lupa.png" alt="Lupa" style={{ width: "30px", height: "30px", objectFit: "contain",marginRight: "0.5rem",marginTop: "0.5rem" }} />
         Evaluacion de Candidatos
          </h2> 
          <hr style={{ 
      width: "100%",
      border: "none",
      borderTop: "2px solid #1E2A1C",
      margin: "0.5rem 0rem 1.5rem  0rem",
      alignSelf: "stretch"
      }} />

          <Desplegable
        label="Seleccionar funcion o puesto a evaluar:"
        opciones={opciones}
        valor={funcion}
        onChange={setFuncion}
      />
      <hr style={{ 
      width: "100%",
      border: "none",
      borderTop: "2px solid #1E2A1C",
      margin: "0.5rem 0rem 1.5rem  0rem",
      alignSelf: "stretch"
      }} />
      <h2 style={{ display: "flex",margin: "1rem auto 0.5rem",  fontSize: "1.5rem" }}>
          Lista de competencias asociadas
          </h2> 
      <div style={{ margin: "2rem" }}>
      <Tabla
        columnas={["Competencias", "Ponderacion requerida"]}
        datos={[
          ["",""],["",""],["",""],["",""],["",""],["",""],["",""],["",""],["",""],["",""]
        ]}
      />
      </div>
      <Link href="/evaluar-candidato/evaluar-candidato-pagina2/evaluar-candidato-pagina3">
      <Boton texto="Siguiente" padding="10px 20px" />
      </Link>
    </div>
    
    </div>
  );
}