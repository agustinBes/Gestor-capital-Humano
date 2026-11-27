"use client";
import { useState } from "react";
import { useEffect } from "react";

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
          <button style={{margin: "1rem auto 0.5rem", fontSize: "2rem", background: "#1E2A1C", color: "#F5F5F5", borderRadius: "4px", padding: "0.5rem 1rem"}} onClick={() => alert("Cuestionario completado")}>
          Evaluar Candidato
          </button>
          
      
    </div>
    
    </div>
  );
}