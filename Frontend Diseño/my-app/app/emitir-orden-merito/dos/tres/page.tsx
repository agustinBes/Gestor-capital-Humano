"use client";

import { Suspense, useEffect, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import Boton from "../../../../componentes/boton";
import Tabla from "../../../../componentes/tabla";
import FeedbackMessage from "@/componentes/feedback-message";
import {
  getErrorMessage,
  type EvaluacionOrdenMeritoDto,
} from "@/lib/backend-api";

function EvaluacionesDelPuesto() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const idPuesto = searchParams.get("idPuesto") ?? "";
  const [resultado, setResultado] = useState<{
    idPuesto: string;
    evaluaciones: EvaluacionOrdenMeritoDto[];
    error: string;
    ready: boolean;
  }>({ idPuesto: "", evaluaciones: [], error: "", ready: false });
  const [seleccionadas, setSeleccionadas] = useState<number[]>([]);
  const [generando, setGenerando] = useState(false);

  useEffect(() => {
    if (!idPuesto) return;

    let activo = true;
    fetch(`/api/backend/emitir-orden-merito/emitir-orden-merito/${encodeURIComponent(idPuesto)}`, {
      cache: "no-store",
    })
      .then(async (response) => {
        if (!response.ok) {
          const detail = await response.text();
          throw new Error(detail || `Error del backend (${response.status})`);
        }
        return (await response.json()) as EvaluacionOrdenMeritoDto[];
      })
      .then((resultado) => {
        if (activo) {
          setResultado({
            idPuesto,
            evaluaciones: resultado,
            error: "",
            ready: true,
          });
          setSeleccionadas([]);
        }
      })
      .catch((solicitudError: unknown) => {
        if (activo) {
          setResultado({
            idPuesto,
            evaluaciones: [],
            error: getErrorMessage(solicitudError),
            ready: true,
          });
        }
      });

    return () => {
      activo = false;
    };
  }, [idPuesto]);

  const cargando = Boolean(idPuesto) && (!resultado.ready || resultado.idPuesto !== idPuesto);
  const evaluaciones = cargando ? [] : resultado.evaluaciones;
  const error = !idPuesto
    ? "No se recibió el identificador del puesto."
    : cargando
      ? ""
      : resultado.error;

  const emitirOrden = async () => {
    if (!idPuesto || seleccionadas.length === 0) return;

    setGenerando(true);
    try {
      const parametros = new URLSearchParams();
      parametros.set("id", idPuesto);
      seleccionadas.forEach((index) => {
        const evaluacion = evaluaciones[index];
        if (evaluacion) parametros.append("cuestionarios", String(evaluacion.idCuestionario));
      });

      const response = await fetch(
        `/api/backend/emitir-orden-merito/generar-reporte/puesto/${encodeURIComponent(idPuesto)}/export?${parametros.toString()}`,
        { cache: "no-store" }
      );
      if (!response.ok) {
        const detail = await response.text();
        throw new Error(detail || `Error del backend (${response.status})`);
      }

      const archivo = await response.blob();
      const url = URL.createObjectURL(archivo);
      const enlace = document.createElement("a");
      enlace.href = url;
      enlace.download = `orden-merito-${idPuesto}.pdf`;
      enlace.click();
      setTimeout(() => URL.revokeObjectURL(url), 1000);
      router.push("/emitir-orden-merito/dos/tres/cuatro");
    } catch (solicitudError: unknown) {
      setResultado((actual) => ({
        ...actual,
        error: getErrorMessage(solicitudError),
      }));
    } finally {
      setGenerando(false);
    }
  };

  const seleccionarFila = (index: number) => {
    setSeleccionadas((actuales) =>
      actuales.includes(index)
        ? actuales.filter((seleccionado) => seleccionado !== index)
        : [...actuales, index]
    );
  };

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
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Evaluaciones del puesto</h2>
        <Tabla
          columnas={["ID del cuestionario", "Candidato", "Fecha", "Estado"]}
          datos={evaluaciones.map((evaluacion) => [
            evaluacion.idCuestionario,
            evaluacion.nombreCandidato,
            evaluacion.fecha,
            evaluacion.estado,
          ])}
          onRowClick={seleccionarFila}
          seleccionados={seleccionadas}
        />
        {cargando && <p role="status">Cargando evaluaciones...</p>}
        {error && <FeedbackMessage variant="error">{error}</FeedbackMessage>}
        {!cargando && !error && evaluaciones.length === 0 && (
          <FeedbackMessage variant="warning">
            El backend no devolvió evaluaciones para este puesto.
          </FeedbackMessage>
        )}
        <div style={{ display: "flex", justifyContent: "center", margin: "1.5rem" }}>
          <Boton
            texto={generando ? "Generando reporte..." : "Emitir orden de mérito"}
            onClick={emitirOrden}
            disabled={generando || cargando || seleccionadas.length === 0}
          />
        </div>
      </section>
    </main>
  );
}

export default function OrdenMeritoEvaluaciones() {
  return (
    <Suspense fallback={<p role="status">Cargando evaluaciones...</p>}>
      <EvaluacionesDelPuesto />
    </Suspense>
  );
}
