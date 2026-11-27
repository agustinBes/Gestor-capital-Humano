"use client";

import { Suspense, useEffect, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import Boton from "../../../componentes/boton";
import Tabla from "../../../componentes/tabla";
import {
  backendRequest,
  getErrorMessage,
  type PuestoOrdenMeritoDto,
} from "@/lib/backend-api";

function ResultadosOrdenMerito() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const codigo = searchParams.get("codigo")?.trim() ?? "";
  const nombre = searchParams.get("puesto")?.trim() ?? "";
  const empresa = searchParams.get("empresa")?.trim() ?? "";
  const parametros = new URLSearchParams();
  if (codigo) parametros.set("codigo", codigo);
  if (nombre) parametros.set("puesto", nombre);
  if (empresa) parametros.set("empresa", empresa);
  const query = parametros.toString();

  const [resultado, setResultado] = useState<{
    query: string;
    puestos: PuestoOrdenMeritoDto[];
    error: string;
    ready: boolean;
  }>({ query: "", puestos: [], error: "", ready: false });
  const [seleccionado, setSeleccionado] = useState<number[]>([]);

  useEffect(() => {
    let activo = true;
    backendRequest<PuestoOrdenMeritoDto[]>(
      `/emitir-orden-merito/buscar${query ? `?${query}` : ""}`
    )
      .then((puestos) => {
        if (activo) setResultado({ query, puestos, error: "", ready: true });
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
  }, [query]);

  const cargando = !resultado.ready || resultado.query !== query;
  const error = cargando ? "" : resultado.error;
  const puestos = cargando ? [] : resultado.puestos;

  const continuar = () => {
    const puesto = seleccionado.length > 0 ? puestos[seleccionado[0]] : undefined;
    if (!puesto) return;
    router.push(`/emitir-orden-merito/dos/tres?idPuesto=${puesto.id}`);
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
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Puestos encontrados</h2>
        <Tabla
          columnas={["Código", "Puesto", "Empresa", "Candidatos", "Evaluaciones completas"]}
          datos={puestos.map((puesto) => [
            puesto.codigo,
            puesto.nombre,
            puesto.empresa,
            puesto.candidatos,
            puesto.evaluacionesCompletadas,
          ])}
          onRowClick={(index) => setSeleccionado([index])}
          seleccionados={seleccionado}
        />
        {cargando && <p role="status">Buscando puestos...</p>}
        {error && <p role="alert">No se pudieron cargar los puestos: {error}</p>}
        {!cargando && !error && puestos.length === 0 && (
          <p>No hay puestos disponibles para esos criterios.</p>
        )}
        <div style={{ display: "flex", justifyContent: "center", margin: "1.5rem" }}>
          <Boton
            texto="Ver evaluaciones"
            onClick={continuar}
            disabled={cargando || Boolean(error) || seleccionado.length === 0}
          />
        </div>
      </section>
    </main>
  );
}

export default function OrdenMeritoResultados() {
  return (
    <Suspense fallback={<p role="status">Cargando búsqueda...</p>}>
      <ResultadosOrdenMerito />
    </Suspense>
  );
}
