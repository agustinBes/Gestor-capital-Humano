"use client";
import { Suspense } from "react";
import { useSearchParams } from "next/navigation";
import Boton from "../../../componentes/boton";
import Tabla from "../../../componentes/tabla";
import Link from "next/link";

// Paso 7: valores requeridos por la función vs obtenidos por el candidato.
// Data de prueba (escala 1 a 5). Después lo reemplazás por tu fetch.
const competencias = [
  { nombre: "Comunicación", requerido: 4, obtenido: 3 },
  { nombre: "Trabajo en equipo", requerido: 5, obtenido: 4 },
  { nombre: "Liderazgo", requerido: 3, obtenido: 4 },
  { nombre: "Resolución de problemas", requerido: 4, obtenido: 5 },
];

const ESCALA_MAX = 5;

function ReporteComparativoContenido() {
  const searchParams = useSearchParams();
  const funcion = searchParams.get("funcion") ?? "";
  const candidato = searchParams.get("candidato") ?? "";

  // Paso 8: imprimir el reporte con el gráfico y la tabla
  const imprimir = () => window.print();

  return (
    <div style={{
      minHeight: "100vh",
      display: "flex",
      justifyContent: "center",
      alignItems: "flex-start",
      paddingTop: "4rem"
    }}>
      {/* Al imprimir se ocultan los botones */}
      <style>{`@media print { .no-imprimir { display: none !important; } }`}</style>

      <div className="flex flex-col flex-1" style={{
        display: "flex",
        maxWidth: "1100px",
        margin: "2rem auto",
        border: "2px solid #1E2A1C",
        borderRadius: "8px",
        background: "#F5F5F5",
        alignItems: "center",
        width: "100%",
        paddingBottom: "2rem"
      }}>
        <h2 style={{ display: "flex", margin: "1rem auto 0.5rem", fontSize: "2rem" }}>
          <img src="/lupa.png" alt="Lupa" style={{ width: "30px", height: "30px", objectFit: "contain", marginRight: "0.5rem", marginTop: "0.5rem" }} />
          Reporte Comparativo
        </h2>

        <hr style={{
          width: "100%",
          border: "none",
          borderTop: "2px solid #1E2A1C",
          margin: "0.5rem 0rem 1.5rem 0rem",
          alignSelf: "stretch"
        }} />

        <p style={{ marginBottom: "1rem" }}>
          <b>Función:</b> {funcion} &nbsp;|&nbsp; <b>Candidato Nro:</b> {candidato}
        </p>

        {/* Paso 7: grilla de valores */}
        <h3 style={{ fontSize: "1.5rem" }}>Valores por competencia</h3>
        <Tabla
          columnas={["Competencia", "Valor requerido", "Valor obtenido"]}
          datos={competencias.map(c => [c.nombre, c.requerido, c.obtenido])}
        />

        {/* Paso 7: gráfico (barras hechas con div, sin librerías) */}
        <h3 style={{ fontSize: "1.5rem", marginTop: "2rem" }}>Gráfico comparativo</h3>
        <div style={{ width: "100%", maxWidth: "800px", padding: "0 2rem", marginTop: "1rem" }}>
          <div style={{ display: "flex", gap: "1.5rem", marginBottom: "1rem", justifyContent: "center" }}>
            <span><span style={{ display: "inline-block", width: 14, height: 14, background: "#1E2A1C", marginRight: 6 }} />Requerido</span>
            <span><span style={{ display: "inline-block", width: 14, height: 14, background: "#6FA06B", marginRight: 6 }} />Obtenido</span>
          </div>

          {competencias.map(c => (
            <div key={c.nombre} style={{ marginBottom: "1rem" }}>
              <p style={{ fontWeight: "bold", marginBottom: "0.25rem" }}>{c.nombre}</p>
              <div style={{ background: "#ddd", height: 18, marginBottom: 4 }}>
                <div style={{ width: `${(c.requerido / ESCALA_MAX) * 100}%`, height: "100%", background: "#1E2A1C" }} />
              </div>
              <div style={{ background: "#ddd", height: 18 }}>
                <div style={{ width: `${(c.obtenido / ESCALA_MAX) * 100}%`, height: "100%", background: "#6FA06B" }} />
              </div>
            </div>
          ))}
        </div>

        {/* Paso 8: Imprimir. Paso 9: termina el CU */}
        <div className="no-imprimir" style={{ display: "flex", gap: "2rem", margin: "2rem 1rem 0" }}>
          <Boton texto="Imprimir" onClick={imprimir} padding="10px 30px" />
          <Link href="/emitir-reporte-comparativo">
            <Boton texto="Volver" padding="10px 30px" />
          </Link>
        </div>
      </div>
    </div>
  );
}

export default function ReporteComparativo() {
  return (
    <Suspense fallback={null}>
      <ReporteComparativoContenido />
    </Suspense>
  );
}