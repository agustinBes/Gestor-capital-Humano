"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Boton from "../../componentes/boton";
import Input2 from "../../componentes/input2";
import Tabla from "../../componentes/tabla";
import FeedbackMessage from "@/componentes/feedback-message";
import {
  backendRequest,
  getErrorMessage,
  type CandidatoDto,
  type CuestionarioDto,
  type PuestoDto,
} from "@/lib/backend-api";

type DatosComparacion = {
  puestos: PuestoDto[];
  candidatos: CandidatoDto[];
  cuestionarios: CuestionarioDto[];
};

export default function EmitirReporteComparativo() {
  const router = useRouter();
  const [empresa, setEmpresa] = useState("");
  const [nombrePuesto, setNombrePuesto] = useState("");
  const [datos, setDatos] = useState<DatosComparacion | null>(null);
  const [puestoSeleccionado, setPuestoSeleccionado] = useState<number | null>(null);
  const [cuestionarioSeleccionado, setCuestionarioSeleccionado] = useState<number | null>(null);
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState("");

  const buscar = async () => {
    setCargando(true);
    setError("");
    setDatos(null);
    setPuestoSeleccionado(null);
    setCuestionarioSeleccionado(null);

    const parametros = new URLSearchParams();
    if (empresa.trim()) parametros.set("empresa", empresa.trim());
    if (nombrePuesto.trim()) parametros.set("puesto", nombrePuesto.trim());
    const query = parametros.toString();

    try {
      const [puestos, candidatos, cuestionarios] = await Promise.all([
        backendRequest<PuestoDto[]>(query ? `/puestos/buscar?${query}` : "/puestos/lista"),
        backendRequest<CandidatoDto[]>("/gestionar-candidatos/lista"),
        backendRequest<CuestionarioDto[]>("/cuestionarios/list"),
      ]);
      setDatos({ puestos, candidatos, cuestionarios });
    } catch (errorBusqueda: unknown) {
      setError(getErrorMessage(errorBusqueda));
    } finally {
      setCargando(false);
    }
  };

  const puesto = datos?.puestos.find((item) => item.idPuesto === puestoSeleccionado);
  const cuestionariosDelPuesto =
    datos?.cuestionarios.filter((item) => item.idPuesto === puestoSeleccionado) ?? [];
  const filasCandidatos = cuestionariosDelPuesto.map((cuestionario) => {
    const candidato = datos?.candidatos.find((item) => item.id === cuestionario.idCandidato);
    const completo =
      cuestionario.bloques.length > 0 &&
      cuestionario.bloques.every((bloque) => bloque.completado);
    return {
      cuestionario,
      fila: [
        candidato?.apellido ?? `ID ${cuestionario.idCandidato}`,
        candidato?.nombre ?? "",
        candidato?.tipo ?? "",
        candidato?.documento ?? "",
        cuestionario.idCuestionario,
        completo ? "Completo" : "Incompleto",
      ],
    };
  });

  const comparar = () => {
    if (!puestoSeleccionado || !cuestionarioSeleccionado) return;
    router.push(
      `/emitir-reporte-comparativo/emitir-reportecomparativo-pagina2?idPuesto=${puestoSeleccionado}&idCuestionario=${cuestionarioSeleccionado}`
    );
  };

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
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Reporte comparativo</h2>
        <div style={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: "1rem" }}>
          <Input2
            label="Empresa"
            placeholder="Nombre de la empresa"
            value={empresa}
            onChange={(event) => setEmpresa(event.target.value)}
          />
          <Input2
            label="Puesto"
            placeholder="Nombre del puesto"
            value={nombrePuesto}
            onChange={(event) => setNombrePuesto(event.target.value)}
          />
        </div>
        <div style={{ textAlign: "center", margin: "1rem" }}>
          <Boton texto="Buscar" onClick={buscar} disabled={cargando} />
        </div>
        {cargando && <p role="status">Consultando datos del backend...</p>}
        {error && (
          <FeedbackMessage variant="error">
            No se pudieron cargar los datos: {error}
          </FeedbackMessage>
        )}

        {datos && (
          <>
            <h3>Puestos recuperados</h3>
            <Tabla
              columnas={["Código", "Puesto", "Empresa"]}
              datos={datos.puestos.map((item) => [item.codigo, item.nombre, item.empresa])}
              onRowClick={(index) => {
                const seleccionado = datos.puestos[index];
                setPuestoSeleccionado(seleccionado?.idPuesto ?? null);
                setCuestionarioSeleccionado(null);
              }}
              seleccionados={datos.puestos.flatMap((item, index) =>
                item.idPuesto === puestoSeleccionado ? [index] : []
              )}
            />
            {datos.puestos.length === 0 && (
              <FeedbackMessage variant="warning">
                No hay puestos para esos filtros.
              </FeedbackMessage>
            )}

            {puesto && (
              <>
                <h3 style={{ marginTop: "2rem" }}>
                  Cuestionarios asociados a {puesto.nombre}
                </h3>
                <Tabla
                  columnas={[
                    "Apellido",
                    "Nombre",
                    "Tipo de documento",
                    "Documento",
                    "ID cuestionario",
                    "Estado",
                  ]}
                  datos={filasCandidatos.map((item) => item.fila)}
                  onRowClick={(index) =>
                    setCuestionarioSeleccionado(
                      filasCandidatos[index]?.cuestionario.idCuestionario ?? null
                    )
                  }
                  seleccionados={filasCandidatos.flatMap((item, index) =>
                    item.cuestionario.idCuestionario === cuestionarioSeleccionado ? [index] : []
                  )}
                />
                {filasCandidatos.length === 0 && (
                  <FeedbackMessage variant="warning">
                    No hay cuestionarios asociados a este puesto en el backend.
                  </FeedbackMessage>
                )}
              </>
            )}

            <div style={{ display: "flex", justifyContent: "center", margin: "1.5rem" }}>
              <Boton
                texto="Ver datos del cuestionario"
                onClick={comparar}
                disabled={!puestoSeleccionado || !cuestionarioSeleccionado}
              />
            </div>
          </>
        )}
      </section>
    </main>
  );
}
