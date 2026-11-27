"use client";

import { useEffect, useState, type FormEvent } from "react";
import Link from "next/link";
import Boton from "../../componentes/boton";
import Tabla from "../../componentes/tabla";
import {
  backendRequest,
  getErrorMessage,
  isPositiveLongIdentifier,
  type CompetenciaDto,
  type CompetenciaDtoCreate,
  type TipoCompetencia,
} from "@/lib/backend-api";

type FormularioCompetencia = CompetenciaDtoCreate;

const formularioVacio: FormularioCompetencia = {
  codigo: "",
  nombre: "",
  descripcion: "",
  tipo: "TECNICA",
};

const controlStyle = {
  width: "100%",
  padding: "0.6rem",
  border: "1px solid #667260",
  borderRadius: "4px",
};

const overlayStyle = {
  position: "fixed" as const,
  inset: 0,
  zIndex: 1000,
  display: "grid",
  placeItems: "center",
  padding: "1rem",
  background: "rgba(0,0,0,0.5)",
};

export default function GestionarCompetencias() {
  const [competencias, setCompetencias] = useState<CompetenciaDto[]>([]);
  const [busqueda, setBusqueda] = useState("");
  const [seleccionada, setSeleccionada] = useState<number | null>(null);
  const [formulario, setFormulario] = useState(formularioVacio);
  const [idEdicion, setIdEdicion] = useState<number | null>(null);
  const [modalAbierto, setModalAbierto] = useState(false);
  const [cargando, setCargando] = useState(true);
  const [guardando, setGuardando] = useState(false);
  const [error, setError] = useState("");
  const [mensaje, setMensaje] = useState("");

  const consultar = async (filtro = "") => {
    setCargando(true);
    setError("");
    setMensaje("");
    try {
      const path = filtro
        ? `/gestionar-competencias/buscar/${encodeURIComponent(filtro)}`
        : "/gestionar-competencias/lista";
      const resultado = await backendRequest<CompetenciaDto[]>(path);
      setCompetencias(resultado);
      setSeleccionada(null);
    } catch (errorSolicitud: unknown) {
      setCompetencias([]);
      setSeleccionada(null);
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setCargando(false);
    }
  };

  useEffect(() => {
    let activo = true;
    backendRequest<CompetenciaDto[]>("/gestionar-competencias/lista")
      .then((resultado) => {
        if (activo) setCompetencias(resultado);
      })
      .catch((errorSolicitud: unknown) => {
        if (activo) setError(getErrorMessage(errorSolicitud));
      })
      .finally(() => {
        if (activo) setCargando(false);
      });

    return () => {
      activo = false;
    };
  }, []);

  const abrirAlta = () => {
    setFormulario(formularioVacio);
    setIdEdicion(null);
    setError("");
    setModalAbierto(true);
  };

  const abrirEdicion = () => {
    const competencia = competencias.find((item) => item.id === seleccionada);
    if (!competencia) return;
    setFormulario({
      codigo: competencia.codigo,
      nombre: competencia.nombre,
      descripcion: competencia.descripcion,
      tipo: competencia.tipo,
    });
    setIdEdicion(competencia.id);
    setError("");
    setModalAbierto(true);
  };

  const guardar = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError("");
    setMensaje("");

    if (!formulario.codigo.trim() || formulario.codigo.length > 30) {
      setError("El código es obligatorio y no puede superar 30 caracteres.");
      return;
    }
    if (!formulario.nombre.trim() || formulario.nombre.length > 50) {
      setError("El nombre es obligatorio y no puede superar 50 caracteres.");
      return;
    }
    if (formulario.descripcion.trim().length < 30 || formulario.descripcion.length > 300) {
      setError("La descripción debe tener entre 30 y 300 caracteres.");
      return;
    }

    setGuardando(true);
    try {
      const body: CompetenciaDtoCreate = {
        ...formulario,
        codigo: formulario.codigo.trim(),
        nombre: formulario.nombre.trim(),
        descripcion: formulario.descripcion.trim(),
      };
      const path =
        idEdicion === null
          ? "/gestionar-competencias/new"
          : `/gestionar-competencias/update/${encodeURIComponent(String(idEdicion))}`;
      const resultado = await backendRequest<CompetenciaDto>(path, {
        method: idEdicion === null ? "POST" : "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });
      setModalAbierto(false);
      await consultar(busqueda.trim());
      setMensaje(
        idEdicion === null
          ? `Competencia creada: ${resultado.nombre}.`
          : `Competencia actualizada: ${resultado.nombre}.`
      );
    } catch (errorSolicitud: unknown) {
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setGuardando(false);
    }
  };

  const eliminar = async () => {
    const competencia = competencias.find((item) => item.id === seleccionada);
    if (!competencia) return;

    const idConsultor = window.prompt(
      "Ingresá el ID numérico del consultor responsable de la eliminación."
    );
    if (idConsultor === null) return;
    const idConsultorLimpio = idConsultor.trim();
    if (!isPositiveLongIdentifier(String(competencia.id)) || !isPositiveLongIdentifier(idConsultorLimpio)) {
      setError("Los IDs de competencia y consultor deben ser enteros positivos válidos.");
      return;
    }

    setError("");
    setMensaje("");
    setCargando(true);
    try {
      const respuesta = await backendRequest<string>(
        `/gestionar-competencias/delete/${encodeURIComponent(String(competencia.id))}/responsable/${encodeURIComponent(idConsultorLimpio)}`,
        { method: "DELETE" }
      );
      await consultar(busqueda.trim());
      setMensaje(respuesta);
    } catch (errorSolicitud: unknown) {
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setCargando(false);
    }
  };

  const cambiarCampo = (campo: keyof FormularioCompetencia, valor: string) => {
    setFormulario((actual) => ({
      ...actual,
      [campo]: valor,
    }));
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
          padding: "1.5rem",
        }}
      >
        <h1 style={{ textAlign: "center", fontSize: "2rem" }}>Gestionar competencias</h1>
        <div style={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: "0.75rem" }}>
          <label>
            Buscar por nombre
            <input
              value={busqueda}
              onChange={(event) => setBusqueda(event.target.value)}
              onKeyDown={(event) => {
                if (event.key === "Enter") void consultar(busqueda.trim());
              }}
              style={{ ...controlStyle, minWidth: "240px" }}
            />
          </label>
          <div style={{ alignSelf: "end" }}>
            <Boton texto="Buscar" onClick={() => void consultar(busqueda.trim())} disabled={cargando} />
            <Boton texto="Limpiar" onClick={() => { setBusqueda(""); void consultar(); }} disabled={cargando} />
          </div>
        </div>

        {cargando && <p role="status">Consultando competencias del backend...</p>}
        {error && <p role="alert">{error}</p>}
        {mensaje && <p role="status">{mensaje}</p>}

        <Tabla
          columnas={["ID", "Código", "Nombre", "Tipo", "Descripción", "Factores asociados"]}
          datos={competencias.map((item) => [
            item.id,
            item.codigo,
            item.nombre,
            item.tipo,
            item.descripcion,
            item.factores.map((factor) => factor.nombre).join(", ") || "Sin factores",
          ])}
          onRowClick={(index) => setSeleccionada(competencias[index]?.id ?? null)}
          seleccionados={competencias.flatMap((item, index) =>
            item.id === seleccionada ? [index] : []
          )}
        />
        {!cargando && !error && competencias.length === 0 && (
          <p>No hay competencias que coincidan con la búsqueda.</p>
        )}

        <div style={{ display: "flex", justifyContent: "center", flexWrap: "wrap", gap: "0.75rem", marginTop: "1rem" }}>
          <Boton texto="Nueva competencia" onClick={abrirAlta} />
          <Boton texto="Modificar" onClick={abrirEdicion} disabled={seleccionada === null || cargando} />
          <Boton texto="Eliminar" onClick={() => void eliminar()} disabled={seleccionada === null || cargando} />
          <Link href="/">
            <Boton texto="Inicio" type="button" />
          </Link>
        </div>
      </section>

      {modalAbierto && (
        <div role="dialog" aria-modal="true" aria-labelledby="titulo-form-competencia" style={overlayStyle}>
          <section style={{ width: "100%", maxWidth: "600px", maxHeight: "90vh", overflowY: "auto", background: "#F5F5F5", borderRadius: "8px", padding: "1.5rem" }}>
            <h2 id="titulo-form-competencia" style={{ textAlign: "center", fontSize: "1.5rem" }}>
              {idEdicion === null ? "Nueva competencia" : "Modificar competencia"}
            </h2>
            <form onSubmit={guardar}>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Código
                <input
                  value={formulario.codigo}
                  onChange={(event) => cambiarCampo("codigo", event.target.value)}
                  maxLength={30}
                  required
                  disabled={idEdicion !== null}
                  style={controlStyle}
                />
              </label>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Nombre
                <input
                  value={formulario.nombre}
                  onChange={(event) => cambiarCampo("nombre", event.target.value)}
                  maxLength={50}
                  required
                  style={controlStyle}
                />
              </label>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Descripción
                <textarea
                  value={formulario.descripcion}
                  onChange={(event) => cambiarCampo("descripcion", event.target.value)}
                  minLength={30}
                  maxLength={300}
                  required
                  rows={4}
                  style={controlStyle}
                />
              </label>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Tipo
                <select
                  value={formulario.tipo}
                  onChange={(event) => cambiarCampo("tipo", event.target.value as TipoCompetencia)}
                  disabled={idEdicion !== null}
                  style={controlStyle}
                >
                  <option value="TECNICA">Técnica</option>
                  <option value="ACTITUDINAL">Actitudinal</option>
                  <option value="GENERICA">Genérica</option>
                </select>
              </label>
              {error && <p role="alert">{error}</p>}
              <div style={{ display: "flex", justifyContent: "center", gap: "0.75rem", marginTop: "1rem" }}>
                <Boton texto={guardando ? "Guardando..." : "Guardar"} type="submit" disabled={guardando} />
                <Boton texto="Cancelar" type="button" onClick={() => setModalAbierto(false)} disabled={guardando} />
              </div>
            </form>
          </section>
        </div>
      )}
    </main>
  );
}
