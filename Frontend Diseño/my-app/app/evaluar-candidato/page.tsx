"use client";
import { useState } from "react";
import Boton from "../../componentes/boton";
import Input from "../../componentes/input";
import Tabla from "../../componentes/tabla";

export default function EvaluarCandidato() {
  const [mostrarTablas, setMostrarTablas] = useState(false);
  return (

    <div style={{
    minHeight: "100vh",
    display: "flex",
    alignItems: mostrarTablas ? "flex-start" : "center",
    justifyContent: "center"
  }}> 
    <div className="flex flex-col flex-1" style={{display: "flex",maxWidth: mostrarTablas ? "1100px" : "800px", margin: "2rem auto", border: "2px solid #1E2A1C", borderRadius: "8px", background: "#F5F5F5", alignItems: "center",width: "100%",paddingBottom: "2rem"}}>
          <h2 style={{ display: "flex",margin: "1rem auto 0.5rem" }}>
          <img src="/lupa.png" alt="Lupa" style={{ width: "25px", height: "25px", objectFit: "contain",marginRight: "0.5rem" }} />
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
      <Boton texto="Evaluar Candidato" onClick={() => setMostrarTablas(true)} padding="20px 20px" />
      </div>

      {mostrarTablas && (
      <div>
     <Tabla
      columnas={["Nro de Candidato", "Nombre", "Apellido"]}
      datos={[
        ["038", "Lucas", "Besigniano"],
        ["069", "Agustina", "Luna"],
      ]}
    />
    <Tabla
      columnas={["Nro de Candidato", "Nombre", "Apellido"]}
      datos={[
        ["016", "Micalea", "Gaitan"],
        ["067", "Ivan", "Cabañas"],
      ]}
    />
     </div>
     )}
    </div>
    </div>
  );
}