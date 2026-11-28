"use client";

import { Suspense, useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import Boton from "../../../componentes/boton";
import Tabla from "../../../componentes/tabla";
import FeedbackMessage from "@/componentes/feedback-message";
import {
  backendRequest,
  getErrorMessage,
  type CandidatoDto,
  type CuestionarioDto,
  type PuestoDto,
} from "@/lib/backend-api";

type DatosReporte = {
  puesto: PuestoDto;
  cuestionario: CuestionarioDto;
  candidato: CandidatoDto | undefined;
};

function DetalleReporte() {
  const searchParams = useSearchParams();
  const idPuesto = searchParams.get("idPuesto") ?? "";
  const idCuestionario = searchParams.get("idCuestionario") ?? "";
  const clave = `${idPuesto}:${idCuestionario}`;
  const [resultado, setResultado] = useState<{
    clave: string;
    datos: DatosReporte | null;
    error: string;
    ready: boolean;
  }>({ clave: "", datos: null, error: "", ready: false });

  useEffect(() => {
    if (!idPuesto || !idCuestionario) return;

    let activo = true;
    Promise.all([
      backendRequest<PuestoDto>(`/puestos/${encodeURIComponent(idPuesto)}`),
      backendRequest<CuestionarioDto[]>("/cuestionarios/list"),
      backendRequest<CandidatoDto[]>("/gestionar-candidatos/lista"),
    ])
      .then(([puesto, cuestionarios, candidatos]) => {
        const cuestionario = cuestionarios.find(
          (item) =>
            String(item.idCuestionario) === idCuestionario &&
            String(item.idPuesto) === idPuesto
        );
        if (!cuestionario) {
          throw new Error("El backend no devolvió el cuestionario para el puesto seleccionado.");
        }

        if (activo) {
          setResultado({
            clave,
            datos: {
              puesto,
              cuestionario,
              candidato: candidatos.find((item) => item.id === cuestionario.idCandidato),
            },
            error: "",
            ready: true,
          });
        }
      })
      .catch((solicitudError: unknown) => {
        if (activo) {
          setResultado({
            clave,
            datos: null,
            error: getErrorMessage(solicitudError),
            ready: true,
          });
        }
      });

    return () => {
      activo = false;
    };
  }, [clave, idCuestionario, idPuesto]);

  const cargando = !resultado.ready || resultado.clave !== clave;
  const error = !idPuesto || !idCuestionario
    ? "Falta el identificador del puesto o del cuestionario."
    : cargando
      ? ""
      : resultado.error;
  const datos = cargando ? null : resultado.datos;
  const respuestas =
    datos?.cuestionario.bloques.flatMap((bloque) =>
      bloque.items.map((item) => {
        const opcion = item.opciones.find((actual) => actual.fila === item.opcionSeleccionada);
        return [
          bloque.numero,
          item.titulo,
          item.pregunta,
          opcion?.descripcion ?? "Sin respuesta registrada",
        ];
      })
    ) ?? [];

  const imprimir = () => window.print();

  return (
    <main style={{ minHeight: "100vh", padding: "4rem 1rem" }}>
      <section
        style={{
          maxWidth: "1100px",
          margin: "0 auto",
          border: "2px solid #1E2A1C",
          borderRadius: "8px",
          background: "#F5F5F5",
          padding: "1rem",
        }}
      >
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Datos de evaluación</h2>
        {cargando && <p role="status">Cargando DTOs del backend...</p>}
        {error && (
          <FeedbackMessage variant="error">
            No se pudo cargar el reporte: {error}
          </FeedbackMessage>
        )}
        {datos && (
          <>
            <p>
              <strong>Puesto:</strong> {datos.puesto.nombre} — {datos.puesto.empresa}
            </p>
            <p>
              <strong>Candidato:</strong>{" "}
              {datos.candidato
                ? `${datos.candidato.nombre} ${datos.candidato.apellido}`
                : `ID ${datos.cuestionario.idCandidato}`}
            </p>
            <p>
              <strong>Cuestionario:</strong> {datos.cuestionario.idCuestionario}
            </p>
            <p role="note">
              El backend todavía no expone un endpoint de reporte comparativo. A continuación
              se muestran los datos reales disponibles en los DTOs de puesto y cuestionario.
            </p>

            <h3>Competencias requeridas por el puesto</h3>
            <Tabla
              columnas={["Competencia", "Ponderación"]}
              datos={datos.puesto.caracteristicas.map((caracteristica) => [
                caracteristica.nombreCompetencia,
                caracteristica.ponderacion,
              ])}
            />
            {datos.puesto.caracteristicas.length === 0 && (
              <p>El DTO del puesto no incluye competencias asociadas.</p>
            )}

            <h3 style={{ marginTop: "2rem" }}>Respuestas registradas</h3>
            <Tabla
              columnas={["Bloque", "Título", "Pregunta", "Opción elegida"]}
              datos={respuestas}
            />
            {respuestas.length === 0 && (
              <p>El DTO del cuestionario no contiene respuestas.</p>
            )}
          </>
        )}

        <div className="no-imprimir" style={{ display: "flex", justifyContent: "center", margin: "1.5rem" }}>
          <Boton texto="Imprimir" onClick={imprimir} disabled={!datos} />
        </div>
      </section>
      <style>{`@media print { .no-imprimir { display: none !important; } }`}</style>
    </main>
  );
}

export default function ReporteComparativoDetalle() {
  return (
    <Suspense fallback={<p role="status">Cargando reporte...</p>}>
      <DetalleReporte />
    </Suspense>
  );
}
