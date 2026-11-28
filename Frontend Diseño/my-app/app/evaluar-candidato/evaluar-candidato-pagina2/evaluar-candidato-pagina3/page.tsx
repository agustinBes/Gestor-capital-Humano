"use client";

import { useState, useSyncExternalStore } from "react";
import Boton from "../../../../componentes/boton";
import Tabla from "../../../../componentes/tabla";
import FeedbackMessage from "@/componentes/feedback-message";
import { getErrorMessage, type EvaluadosDto } from "@/lib/backend-api";

const suscribirHidratacion = () => () => {};
const obtenerEstadoCliente = () => true;
const obtenerEstadoServidor = () => false;

function leerEvaluados(): EvaluadosDto[] {
  if (typeof window === "undefined") return [];
  const datos = sessionStorage.getItem("candidatosEvaluadosDto");
  return datos ? (JSON.parse(datos) as EvaluadosDto[]) : [];
}

export default function EvaluarCandidatoPagina3() {
  const hidratado = useSyncExternalStore(
    suscribirHidratacion,
    obtenerEstadoCliente,
    obtenerEstadoServidor
  );
  const [evaluados] = useState(leerEvaluados);
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState("");

  const exportar = async () => {
    setCargando(true);
    setError("");
    try {
      const response = await fetch("/api/backend/gestion-cuestionarios/candidatos/export", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(evaluados),
      });
      if (!response.ok) {
        const detail = await response.text();
        throw new Error(detail || `Error del backend (${response.status})`);
      }

      const archivo = await response.blob();
      const url = URL.createObjectURL(archivo);
      const enlace = document.createElement("a");
      enlace.href = url;
      enlace.download = "evaluados.csv";
      enlace.click();
      URL.revokeObjectURL(url);
    } catch (errorExportacion: unknown) {
      setError(getErrorMessage(errorExportacion));
    } finally {
      setCargando(false);
    }
  };

  if (!hidratado) return null;

  return (
    <main style={{ minHeight: "100vh", padding: "4rem 1rem" }}>
      <section
        style={{
          maxWidth: "1000px",
          margin: "0 auto",
          border: "2px solid #1E2A1C",
          borderRadius: "8px",
          background: "#F5F5F5",
          padding: "1rem",
        }}
      >
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>
          Candidatos incluidos en la evaluación
        </h2>
        <Tabla
          columnas={["Nombre", "Apellido", "Tipo de documento", "Documento", "Clave de ingreso"]}
          datos={evaluados.map((candidato) => [
            candidato.nombre,
            candidato.apellido,
            candidato["tipoDocumento de Documento"],
            candidato["Numero de Documento"],
            candidato["clave de ingreso"],
          ])}
        />
        {evaluados.length === 0 && (
          <FeedbackMessage variant="warning">
            No hay una respuesta DTO disponible. Generá los cuestionarios desde el paso anterior.
          </FeedbackMessage>
        )}
        {error && (
          <FeedbackMessage variant="error">
            No se pudo exportar el archivo: {error}
          </FeedbackMessage>
        )}
        <div style={{ display: "flex", justifyContent: "center", margin: "1.5rem" }}>
          <Boton
            texto={cargando ? "Exportando..." : "Exportar candidatos"}
            onClick={exportar}
            disabled={cargando || evaluados.length === 0}
          />
        </div>
      </section>
    </main>
  );
}
