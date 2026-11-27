"use client";
import { useState } from "react";
import { useEffect } from "react";
import Boton from "../../componentes/boton";
import Link from "next/dist/client/link";

export default function CompletarCuestionario() {
 
    const [paddingArriba, setPaddingArriba] = useState("12rem");
  useEffect(() => {const ajustarPadding = () => {if (window.innerWidth < 1400) {setPaddingArriba("1rem");
     } else {setPaddingArriba("12rem");  }};
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
          <img src="/nota.png" alt="Lupa" style={{ width: "30px", height: "30px", objectFit: "contain",marginRight: "0.5rem",marginTop: "0.5rem" }} />
         Cuestionario - Instrucciones
          </h2> 
          <hr style={{ 
      width: "100%",
      border: "none",
      borderTop: "2px solid #1E2A1C",
      margin: "0.5rem 0rem 1.5rem  0rem",
      alignSelf: "stretch"
      }} />
      <label style={{ margin: "1rem", fontSize: "1.2rem", textAlign: "center" }}>
      Por favor, lea cuidadosamente las instrucciones antes de comenzar el cuestionario. Asegúrese de tener suficiente tiempo y un ambiente tranquilo para completar todas las preguntas. Una vez que haya terminado, haga clic en &quot;Finalizar&quot; para enviar sus respuestas.
      </label>
      <div style={{display: "flex", gap: "150px", textAlign: "center", margin: "1rem 0" }}>
      <Boton texto="Cancelar" onClick={() => alert("Guardado")} padding="10px 20px" />
      <Link href="/completar-cuestionario/completar-cuestionario-pagina2">
      <Boton texto="Aceptar" padding="10px 20px" />
      </Link>
      </div>
    </div>
    
    </div>
  );
}