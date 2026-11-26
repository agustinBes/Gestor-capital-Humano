"use client";
import { useState } from "react";
import Boton from "../../../../componentes/boton";
import Tabla from "../../../../componentes/tabla";


export default function EvaluarCandidatoPagina3() {
  
   const [mostrarModal, setMostrarModal] = useState(false);


  return (

    <div style={{
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "flex-start", 
  paddingTop: "12rem"
  }}> 

     



    <div className="flex flex-col flex-1" style={{display: "flex",maxWidth: "800px", margin: "2rem auto", border: "2px solid #1E2A1C", borderRadius: "8px", background: "#F5F5F5", alignItems: "center",width: "100%",paddingBottom: "2rem"}}>
          <h2 style={{ display: "flex",margin: "1rem auto 0.5rem", fontSize: "2rem" }}>
          <img src="/lupa.png" alt="Lupa" style={{ width: "30px", height: "30px", objectFit: "contain",marginRight: "0.5rem",marginTop: "0.5rem" }} />
          Lista de Candidatos a Evaluar
          </h2> 
      <hr style={{ 
      width: "100%",
      border: "none",
      borderTop: "2px solid #1E2A1C",
      margin: "0.5rem 0rem 1.5rem  0rem",
      alignSelf: "stretch"
      }} />
      <div style={{ margin: "2rem" }}>  
      <Tabla
        columnas={["Nombre", "Apellido requerida","Clave de Ingreso","Nro de Documento","Tipo de Documento"]}
        datos={[
          ["","","","",""],["","","","",""],["","","","",""],["","","","",""],["","","","",""],["","","","",""],["","","","",""],["","","","",""],["","","","",""],["","","","",""]
        ]}
      />
      </div>
      <div style={{display: "flex", gap: "150px", textAlign: "center", margin: "1rem 0" }}>
      <Boton texto="Cancelar" onClick={() => alert("Guardado")} padding="10px 20px" />
      <Boton texto="Finalizar" onClick={() => setMostrarModal(true)} padding="10px 20px" />
      </div>
      </div>
    

     {mostrarModal && (
  <div style={{
    position: "fixed",
    top: 0,
    left: 0,
    width: "100vw",
    height: "100vh",
    background: "rgba(0,0,0,0.5)",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    zIndex: 1000
  }}>
    <div style={{
      background: "#f1f1f1",
      padding: "2rem",
      border: "1px solid #333",
      borderRadius: "4px",
      textAlign: "center",
      maxWidth: "400px"
    }}>
      <p>¿Desea exportar los datos de los candidatos con sus claves a un archivo Excel?</p>
      <div style={{ display: "flex", gap: "1rem", justifyContent: "center", marginTop: "1.5rem" }}>
        <Boton texto="Cancelar" onClick={() => setMostrarModal(false)} />
        <Boton texto="Aceptar" onClick={() => setMostrarModal(false)} />
      </div>
    </div>
  </div>
)}





    </div>
    
     
    
    


    
  );
}