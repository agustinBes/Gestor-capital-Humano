"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Boton from "../../componentes/boton";
import Input2 from "../../componentes/input2";
import Tabla from "../../componentes/tabla";
import {
  backendRequest,
  getErrorMessage,
  isPositiveLongIdentifier,
  type CandidatoDto,
} from "@/lib/backend-api";

export default function EvaluarCandidato() {
  const router = useRouter();
  const [numero, setNumero] = useState("");
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [candidatos, setCandidatos] = useState<CandidatoDto[]>([]);
  const [aEvaluar, setAEvaluar] = useState<CandidatoDto[]>([]);
  const [seleccionados, setSeleccionados] = useState<number[]>([]);
  const [busquedaRealizada, setBusquedaRealizada] = useState(false);
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState("");

  const buscarCandidatos = async () => {
    const idCandidato = numero.trim();
    if (idCandidato && !isPositiveLongIdentifier(idCandidato)) {
      setError("El ID del candidato debe ser un entero positivo dentro del rango Long.");
      setCandidatos([]);
      setSeleccionados([]);
      setBusquedaRealizada(false);
      return;
    }

    const parametros = new URLSearchParams();
    if (idCandidato) parametros.set("id", idCandidato);
    if (nombre.trim()) parametros.set("nombre", nombre.trim());
    if (apellido.trim()) parametros.set("apellido", apellido.trim());

    setCargando(true);
    setError("");
    setBusquedaRealizada(true);
    try {
      const query = parametros.toString();
      const resultado = await backendRequest<CandidatoDto[]>(
        query ? `/gestionar-candidatos/buscar?${query}` : "/gestionar-candidatos/lista"
      );
      setCandidatos(resultado);
      setSeleccionados([]);
    } catch (errorBusqueda: unknown) {
      setCandidatos([]);
      setError(getErrorMessage(errorBusqueda));
    } finally {
      setCargando(false);
    }
  };

  const seleccionarFila = (index: number) => {
    const candidatoId = candidatos[index]?.id;
    if (candidatoId === undefined) return;
    setSeleccionados((actuales) =>
      actuales.includes(candidatoId)
        ? actuales.filter((id) => id !== candidatoId)
        : [...actuales, candidatoId]
    );
  };

  const agregarSeleccionados = () => {
    const nuevos = candidatos.filter(
      (candidato) =>
        seleccionados.includes(candidato.id) &&
        !aEvaluar.some((existente) => existente.id === candidato.id)
    );
    setAEvaluar((actuales) => [...actuales, ...nuevos]);
    setSeleccionados([]);
  };

  const continuar = () => {
    const parametros = new URLSearchParams();
    aEvaluar.forEach((candidato) => parametros.append("candidatos", String(candidato.id)));
    router.push(`/evaluar-candidato/evaluar-candidato-pagina2?${parametros.toString()}`);
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
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Búsqueda de candidatos</h2>
        <div style={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: "1rem" }}>
          <Input2
            label="Nro. candidato"
            placeholder="ID del candidato"
            value={numero}
            type="number"
            min={1}
            step={1}
            onChange={(event) => setNumero(event.target.value)}
          />
          <Input2
            label="Nombre"
            placeholder="Nombre"
            value={nombre}
            onChange={(event) => setNombre(event.target.value)}
          />
          <Input2
            label="Apellido"
            placeholder="Apellido"
            value={apellido}
            onChange={(event) => setApellido(event.target.value)}
          />
        </div>
        <div style={{ textAlign: "center", margin: "1rem" }}>
          <Boton texto="Buscar candidatos" onClick={buscarCandidatos} disabled={cargando} />
        </div>

        {cargando && <p role="status">Consultando candidatos en el backend...</p>}
        {error && <p role="alert">No se pudieron cargar los candidatos: {error}</p>}
        {busquedaRealizada && !cargando && !error && (
          <>
            <h3>Candidatos encontrados</h3>
            <Tabla
              columnas={["Nro. de candidato", "Nombre", "Apellido"]}
              datos={candidatos.map((candidato) => [
                candidato.id,
                candidato.nombre,
                candidato.apellido,
              ])}
              onRowClick={seleccionarFila}
              seleccionados={candidatos.flatMap((candidato, index) =>
                seleccionados.includes(candidato.id) ? [index] : []
              )}
            />
            {candidatos.length === 0 && <p>No hay candidatos para esos filtros.</p>}
            <div style={{ display: "flex", justifyContent: "center", gap: "1rem", margin: "1rem" }}>
              <Boton
                texto="Agregar seleccionados"
                onClick={agregarSeleccionados}
                disabled={seleccionados.length === 0}
              />
            </div>
          </>
        )}

        <h3>Candidatos a evaluar</h3>
        <Tabla
          columnas={["Nro. de candidato", "Nombre", "Apellido"]}
          datos={aEvaluar.map((candidato) => [
            candidato.id,
            candidato.nombre,
            candidato.apellido,
          ])}
        />
        <div style={{ display: "flex", justifyContent: "center", margin: "1rem" }}>
          <Boton
            texto="Siguiente"
            onClick={continuar}
            disabled={aEvaluar.length === 0}
          />
        </div>
      </section>
    </main>
  );
}
