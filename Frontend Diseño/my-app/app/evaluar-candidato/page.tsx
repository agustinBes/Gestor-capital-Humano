"use client";
import { useEffect, useState } from "react";
import Boton from "../../componentes/boton";
import Input from "../../componentes/input";
import Tabla from "../../componentes/tabla";
import Link from "next/dist/client/link";

export default function EvaluarCandidato() {
  const [mostrarTablas, setMostrarTablas] = useState(false);
  const [paddingArriba, setPaddingArriba] = useState("17rem");
  const [candidatos, setCandidatos] = useState([
  ["038", "Lucas", "Besigniano"],
  ["069", "Agustina", "Luna"],
  ["016", "Micalea", "Gaitan"],
  ["067", "Ivan", "Cabañas"],
  ]);

  const [aEvaluar, setAEvaluar] = useState<(string | number)[][]>([]);
  const [seleccionados, setSeleccionados] = useState<number[]>([]);

  const seleccionDeFilas = (index: number) => {
  if (seleccionados.includes(index)) {
    setSeleccionados(seleccionados.filter(i => i !== index));
  } else {
    setSeleccionados([...seleccionados, index]);
  }
  };

  const agregarSeleccionados = () => {
    const nuevos = seleccionados.map(i => candidatos[i]);
    setAEvaluar([...aEvaluar, ...nuevos]);
    setSeleccionados([]);
  };

  useEffect(() => {const ajustarPadding = () => {if (window.innerWidth < 1400) {setPaddingArriba("4rem");
   } else {setPaddingArriba("17rem");  }};
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
    <div className="flex flex-col flex-1" style={{display: "flex",maxWidth: mostrarTablas ? "1100px" : "800px", margin: "2rem auto", border: "2px solid #1E2A1C", borderRadius: "8px", background: "#F5F5F5", alignItems: "center",width: "100%",paddingBottom: "2rem"}}>
          <h2 style={{ display: "flex",margin: "1rem auto 0.5rem", fontSize: "2rem" }}>
          <img src="/lupa.png" alt="Lupa" style={{ width: "30px", height: "30px", objectFit: "contain",marginRight: "0.5rem",marginTop: "0.5rem" }} />
          Busqueda de Candidatos
          </h2> 
      
      <hr style={{ 
      width: "100%",
      border: "none",
      borderTop: "2px solid #1E2A1C",
      margin: "0.5rem 0rem 1.5rem  0rem",
      alignSelf: "stretch"
      }} />

      <div style={{marginBottom: "10px"}}>
        <Input label="Nro. Candidato" placeholder="Ingrese el número de candidato" />
      </div>
      <div style={{marginBottom: "10px", display: "flex", gap: "2rem"}}>
        <div style={{flex: "1"}}>
        <Input label="Nombre del Candidato" placeholder="Ingrese el nombre del candidato" />
        </div>
        <div style={{flex: "1"}}>
        <Input label="Apellido del Candidato" placeholder="Ingrese el apellido del candidato" />
        </div>
      </div >
      <div style={{ textAlign: "center", margin: "1rem" }}>
      <Boton texto="Buscar Candidato" onClick={() => {setMostrarTablas(true),setPaddingArriba("1rem");}} padding="20px 20px" />
      </div>

      {mostrarTablas && (

      <div style={{ marginTop: "2rem",width: "100%"}}>
      <hr style={{ 
      width: "100%",
      border: "none",
      borderTop: "2px solid #1E2A1C",
      margin: "0.5rem 0rem 1.5rem 0rem",
      alignSelf: "stretch"
      }} />
      </div>
       )}


       {mostrarTablas && (
        <div style={{ marginTop: "2rem"}}>
      <h3 style={{fontSize: "1.5rem" }}>Candidatos encontrados</h3>
       <div style={{ position: "relative", display: "inline-block"}}>
      
     <Tabla
      columnas={["Nro de Candidato", "Nombre", "Apellido"]}
      datos={candidatos}
      onRowClick={seleccionDeFilas}
      seleccionados={seleccionados}
    />
    
    <div style={{ 
    position: "absolute", 
    right: "-280px",  
    top: "140px" 
  }}>
    <Boton texto="Agregar Candidatos" onClick={agregarSeleccionados} padding="10px 20px" />
    </div>
    </div>
     <h3 style={{fontSize: "1.5rem" }}>Candidatos a evaluar</h3>


     <div style={{ position: "relative", display: "inline-block"}}>
    <Tabla
      columnas={["Nro de Candidato", "Nombre", "Apellido"]}
      datos={aEvaluar}
    />
    <div style={{ 
    position: "absolute", 
    right: "-260px",  
    top: "10px" 
     }}>
    <Link href="/evaluar-candidato/evaluar-candidato-pagina2">
    <Boton texto="Siguiente" padding="10px 20px" />
    </Link>
    </div>
    </div>
    </div>
     )}
    </div>
    </div>
  );
}