"use client";

import { Suspense, useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import Boton from "../../../componentes/boton";
import Tabla from "../../../componentes/tabla";
import { backendRequest, getErrorMessage, type PuestoDto } from "@/lib/backend-api";

function ResultadosPuestos() {
  const searchParams = useSearchParams();
  const codigo = searchParams.get("codigo")?.trim() ?? "";
  const empresa = searchParams.get("empresa")?.trim() ?? "";
  const nombre = searchParams.get("puesto")?.trim() ?? "";
  const [resultado, setResultado] = useState<{
    query: string;
    puestos: PuestoDto[];
    error: string;
    ready: boolean;
  }>({ query: "", puestos: [], error: "", ready: false });
  const [seleccionado, setSeleccionado] = useState<number[]>([]);

  useEffect(() => {
    let activo = true;
    const parametros = new URLSearchParams();
    if (codigo) parametros.set("codigo", codigo);
    if (empresa) parametros.set("empresa", empresa);
    if (nombre) parametros.set("puesto", nombre);
    const query = parametros.toString();

    backendRequest<PuestoDto[]>(
      query ? `/puestos/buscar?${query}` : "/puestos/lista"
    )
      .then((resultados) => {
        if (activo) setResultado({ query, puestos: resultados, error: "", ready: true });
      })
      .catch((solicitudError: unknown) => {
        if (activo) {
          setResultado({
            query,
            puestos: [],
            error: getErrorMessage(solicitudError),
            ready: true,
          });
        }
      });

    return () => {
      activo = false;
    };
  }, [codigo, empresa, nombre]);

  const parametrosActuales = new URLSearchParams();
  if (codigo) parametrosActuales.set("codigo", codigo);
  if (empresa) parametrosActuales.set("empresa", empresa);
  if (nombre) parametrosActuales.set("puesto", nombre);
  const query = parametrosActuales.toString();
  const cargando = !resultado.ready || resultado.query !== query;
  const puestos = cargando ? [] : resultado.puestos;
  const error = cargando ? "" : resultado.error;
  const puestosTabla = puestos.map((puesto) => [
    puesto.codigo,
    puesto.nombre,
    puesto.empresa,
  ]);

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
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Puestos encontrados</h2>
        <Tabla
          columnas={["Código", "Nombre del puesto", "Empresa"]}
          datos={puestosTabla}
          onRowClick={(index) => setSeleccionado([index])}
          seleccionados={seleccionado}
        />
        {cargando && <p role="status">Cargando puestos...</p>}
        {error && <p role="alert">No se pudieron cargar los puestos: {error}</p>}
        {!cargando && !error && puestos.length === 0 && (
          <p>No hay puestos que coincidan con la búsqueda.</p>
        )}
        {!cargando && !error && puestos.length > 0 && (
          <div style={{ display: "flex", justifyContent: "center", gap: "1rem", marginTop: "1rem" }}>
            <Boton
              texto="Modificar"
              type="button"
              disabled={seleccionado.length === 0}
              onClick={() => alert("La modificación de puestos no está disponible en esta pantalla.")}
            />
            <Boton
              texto="Eliminar"
              type="button"
              disabled={seleccionado.length === 0}
              onClick={() => alert("La eliminación de puestos no está disponible en esta pantalla.")}
            />
          </div>
        )}
      </section>
    </main>
  );
}

export default function GestionarPuestosResultados() {
  return (
    <Suspense fallback={<p role="status">Cargando búsqueda...</p>}>
      <ResultadosPuestos />
    </Suspense>
  );
}
