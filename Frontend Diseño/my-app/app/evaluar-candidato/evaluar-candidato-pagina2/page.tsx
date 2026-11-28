"use client";

import { Suspense, useEffect, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import Boton from "../../../componentes/boton";
import Tabla from "../../../componentes/tabla";
import FeedbackMessage from "@/componentes/feedback-message";
import {
  backendRequest,
  getErrorMessage,
  type EvaluadosDto,
  type PuestoDto,
} from "@/lib/backend-api";

function SeleccionarPuesto() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const idsCandidatos = searchParams.getAll("candidatos").join(",");
  const candidatos = idsCandidatos
    .split(",")
    .filter(Boolean)
    .map(Number)
    .filter((id) => Number.isSafeInteger(id) && id > 0);
  const [puestos, setPuestos] = useState<PuestoDto[]>([]);
  const [puestoId, setPuestoId] = useState("");
  const [cargando, setCargando] = useState(true);
  const [generando, setGenerando] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    let activo = true;
    backendRequest<PuestoDto[]>("/puestos/lista")
      .then((resultado) => {
        if (activo) setPuestos(resultado);
      })
      .catch((solicitudError: unknown) => {
        if (activo) setError(getErrorMessage(solicitudError));
      })
      .finally(() => {
        if (activo) setCargando(false);
      });

    return () => {
      activo = false;
    };
  }, []);

  const puestoSeleccionado = puestos.find((puesto) => String(puesto.idPuesto) === puestoId);

  const generarEvaluaciones = async () => {
    if (!puestoId || candidatos.length === 0) return;

    setGenerando(true);
    setError("");
    try {
      const resultado = await backendRequest<EvaluadosDto[]>(
        `/gestion-cuestionarios/nuevo-cuestionario/${puestoId}`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(candidatos),
        }
      );
      sessionStorage.setItem("candidatosEvaluadosDto", JSON.stringify(resultado));
      router.push("/evaluar-candidato/evaluar-candidato-pagina2/evaluar-candidato-pagina3");
    } catch (solicitudError: unknown) {
      setError(getErrorMessage(solicitudError));
    } finally {
      setGenerando(false);
    }
  };

  return (
    <main style={{ minHeight: "100vh", padding: "4rem 1rem" }}>
      <section
        style={{
          maxWidth: "900px",
          margin: "0 auto",
          border: "2px solid #1E2A1C",
          borderRadius: "8px",
          background: "#F5F5F5",
          padding: "1rem",
        }}
      >
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Evaluar candidatos</h2>
        <p>Candidatos seleccionados: {candidatos.length}</p>
        {cargando && <p role="status">Cargando puestos...</p>}
        {error && (
          <FeedbackMessage variant="error">
            No se pudo completar la operación: {error}
          </FeedbackMessage>
        )}

        {!cargando && (
          <div style={{ margin: "1rem 0" }}>
            <label htmlFor="puesto-evaluacion">Puesto a evaluar: </label>
            <select
              id="puesto-evaluacion"
              value={puestoId}
              onChange={(event) => setPuestoId(event.target.value)}
              style={{ padding: "0.5rem", minWidth: "260px" }}
            >
              <option value="">Seleccionar puesto</option>
              {puestos.map((puesto) => (
                <option key={puesto.idPuesto} value={puesto.idPuesto}>
                  {puesto.codigo} — {puesto.nombre} ({puesto.empresa})
                </option>
              ))}
            </select>
            {!error && puestos.length === 0 && (
              <FeedbackMessage variant="warning">
                No hay puestos disponibles en el backend.
              </FeedbackMessage>
            )}
          </div>
        )}

        {puestoSeleccionado && (
          <>
            <h3>Competencias requeridas por el puesto</h3>
            <Tabla
              columnas={["Competencia", "Ponderación"]}
              datos={puestoSeleccionado.caracteristicas.map((caracteristica) => [
                caracteristica.nombreCompetencia,
                caracteristica.ponderacion,
              ])}
            />
            {puestoSeleccionado.caracteristicas.length === 0 && (
              <p>El DTO del puesto no incluye competencias asociadas.</p>
            )}
          </>
        )}

        <div style={{ display: "flex", justifyContent: "center", margin: "1.5rem" }}>
          <Boton
            texto={generando ? "Generando..." : "Generar cuestionarios"}
            onClick={generarEvaluaciones}
            disabled={generando || cargando || candidatos.length === 0 || !puestoId}
          />
        </div>
        {candidatos.length === 0 && (
          <FeedbackMessage variant="error">
            No se recibieron IDs de candidatos. Volvé a seleccionarlos.
          </FeedbackMessage>
        )}
      </section>
    </main>
  );
}

export default function EvaluarCandidatoPagina2() {
  return (
    <Suspense fallback={<p role="status">Cargando selección...</p>}>
      <SeleccionarPuesto />
    </Suspense>
  );
}
