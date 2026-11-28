"use client";

import { useEffect, useState, type FormEvent } from "react";
import Link from "next/link";
import Boton from "../../componentes/boton";
import Tabla from "../../componentes/tabla";
import FeedbackMessage from "@/componentes/feedback-message";
import {
  backendRequest,
  DEFAULT_DELETE_CONSULTOR_ID,
  getErrorMessage,
  isPositiveLongIdentifier,
  type CompetenciaDtoSinFactores,
  type FactorDto,
  type FactorDtoCreate,
} from "@/lib/backend-api";

type FormularioFactor = {
  id_competencia: string;
  codigo: string;
  nombre: string;
  descripcion: string;
  nroOrden: string;
};

const formularioVacio: FormularioFactor = {
  id_competencia: "",
  codigo: "",
  nombre: "",
  descripcion: "",
  nroOrden: "1",
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

export default function GestionarFactores() {
  const [factores, setFactores] = useState<FactorDto[]>([]);
  const [competencias, setCompetencias] = useState<CompetenciaDtoSinFactores[]>([]);
  const [busquedaFactor, setBusquedaFactor] = useState("");
  const [busquedaCompetencia, setBusquedaCompetencia] = useState("");
  const [seleccionado, setSeleccionado] = useState<number | null>(null);
  const [formulario, setFormulario] = useState(formularioVacio);
  const [idEdicion, setIdEdicion] = useState<number | null>(null);
  const [modalAbierto, setModalAbierto] = useState(false);
  const [cargando, setCargando] = useState(false);
  const [cargandoCatalogo, setCargandoCatalogo] = useState(true);
  const [consultaRealizada, setConsultaRealizada] = useState(false);
  const [guardando, setGuardando] = useState(false);
  const [error, setError] = useState("");
  const [mensaje, setMensaje] = useState("");

  const consultar = async (filtroFactor = "", filtroCompetencia = "") => {
    if (!filtroFactor && !filtroCompetencia) {
      setFactores([]);
      setSeleccionado(null);
      setConsultaRealizada(false);
      return;
    }

    setConsultaRealizada(true);
    setCargando(true);
    setError("");
    setMensaje("");
    try {
      const parametros = new URLSearchParams();
      if (filtroFactor) parametros.set("factor", filtroFactor);
      if (filtroCompetencia) parametros.set("competencia", filtroCompetencia);
      const query = parametros.toString();
      const resultado = await backendRequest<FactorDto[]>(`/factores/buscar?${query}`);
      setFactores(resultado);
      setSeleccionado(null);
    } catch (errorSolicitud: unknown) {
      setFactores([]);
      setSeleccionado(null);
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setCargando(false);
    }
  };

  useEffect(() => {
    let activo = true;
    backendRequest<CompetenciaDtoSinFactores[]>("/gestionar-competencias/lista")
      .then((resultadoCompetencias) => {
        if (activo) setCompetencias(resultadoCompetencias);
      })
      .catch((errorSolicitud: unknown) => {
        if (activo) setError(getErrorMessage(errorSolicitud));
      })
      .finally(() => {
        if (activo) setCargandoCatalogo(false);
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
    const factor = factores.find((item) => item.id === seleccionado);
    if (!factor) return;
    setFormulario({
      id_competencia: String(factor.competencia.id),
      codigo: factor.codigo,
      nombre: factor.nombre,
      descripcion: factor.descripcion,
      nroOrden: String(factor.orden),
    });
    setIdEdicion(factor.id);
    setError("");
    setModalAbierto(true);
  };

  const guardar = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError("");
    setMensaje("");

    if (!isPositiveLongIdentifier(formulario.id_competencia)) {
      setError("Seleccioná una competencia válida.");
      return;
    }
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
    const nroOrden = Number(formulario.nroOrden);
    if (!Number.isInteger(nroOrden) || nroOrden < 1 || nroOrden > 32767) {
      setError("El número de orden debe ser un entero entre 1 y 32767.");
      return;
    }

    setGuardando(true);
    try {
      const body: FactorDtoCreate = {
        id_competencia: Number(formulario.id_competencia),
        codigo: formulario.codigo.trim(),
        nombre: formulario.nombre.trim(),
        descripcion: formulario.descripcion.trim(),
        nroOrden,
      };
      const path =
        idEdicion === null
          ? "/factores/new"
          : `/factores/update/${encodeURIComponent(String(idEdicion))}`;
      const resultado = await backendRequest<FactorDto>(path, {
        method: idEdicion === null ? "POST" : "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });
      setModalAbierto(false);
      if (busquedaFactor.trim() || busquedaCompetencia.trim()) {
        await consultar(busquedaFactor.trim(), busquedaCompetencia.trim());
      } else {
        setFactores([]);
        setSeleccionado(null);
        setConsultaRealizada(false);
      }
      setMensaje(
        idEdicion === null
          ? `Factor creado: ${resultado.nombre}.`
          : `Factor actualizado: ${resultado.nombre}.`
      );
    } catch (errorSolicitud: unknown) {
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setGuardando(false);
    }
  };

  const eliminar = async () => {
    const factor = factores.find((item) => item.id === seleccionado);
    if (!factor) return;
    const idConsultorLimpio = DEFAULT_DELETE_CONSULTOR_ID;
    const idFactor = String(factor.id);
    if (
      !isPositiveLongIdentifier(idFactor) ||
      !isPositiveLongIdentifier(idConsultorLimpio)
    ) {
      setError("Los IDs de factor y consultor deben ser enteros positivos válidos.");
      return;
    }

    setCargando(true);
    setError("");
    setMensaje("");
    try {
      const respuesta = await backendRequest<string>(
        `/factores/delete/${encodeURIComponent(idFactor)}/responsable/${encodeURIComponent(idConsultorLimpio)}`,
        { method: "DELETE" }
      );
      if (busquedaFactor.trim() || busquedaCompetencia.trim()) {
        await consultar(busquedaFactor.trim(), busquedaCompetencia.trim());
      } else {
        setFactores([]);
        setSeleccionado(null);
        setConsultaRealizada(false);
      }
      setMensaje(respuesta);
    } catch (errorSolicitud: unknown) {
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setCargando(false);
    }
  };

  const cambiarCampo = (campo: keyof FormularioFactor, valor: string) => {
    setFormulario((actual) => ({ ...actual, [campo]: valor }));
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
        <h1 style={{ textAlign: "center", fontSize: "2rem" }}>Gestionar factores</h1>
        <div style={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: "0.75rem" }}>
          <label>
            Factor
            <input value={busquedaFactor} onChange={(event) => setBusquedaFactor(event.target.value)} style={controlStyle} />
          </label>
          <label>
            Competencia
            <input value={busquedaCompetencia} onChange={(event) => setBusquedaCompetencia(event.target.value)} style={controlStyle} />
          </label>
          <div style={{ alignSelf: "end", display: "flex", gap: "0.5rem" }}>
            <Boton
              texto="Buscar"
              onClick={() => void consultar(busquedaFactor.trim(), busquedaCompetencia.trim())}
              disabled={cargando || (!busquedaFactor.trim() && !busquedaCompetencia.trim())}
            />
            <Boton
              texto="Limpiar"
              onClick={() => {
                setBusquedaFactor("");
                setBusquedaCompetencia("");
                setFactores([]);
                setSeleccionado(null);
                setConsultaRealizada(false);
                setError("");
                setMensaje("");
              }}
              disabled={cargando}
            />
          </div>
        </div>

        {cargando && <p role="status">Consultando factores del backend...</p>}
        {cargandoCatalogo && <p role="status">Cargando competencias para los formularios...</p>}
        {error && <FeedbackMessage variant="error">{error}</FeedbackMessage>}
        {mensaje && <FeedbackMessage variant="success">{mensaje}</FeedbackMessage>}

        {consultaRealizada && (
          <Tabla
            columnas={["ID", "Código", "Factor", "Competencia", "Orden", "Descripción"]}
            datos={factores.map((item) => [
              item.id,
              item.codigo,
              item.nombre,
              item.competencia.nombre,
              item.orden,
              item.descripcion,
            ])}
            onRowClick={(index) => setSeleccionado(factores[index]?.id ?? null)}
            seleccionados={factores.flatMap((item, index) =>
              item.id === seleccionado ? [index] : []
            )}
          />
        )}
        {!consultaRealizada && (
          <FeedbackMessage variant="warning">
            Ingresá un filtro y seleccioná Buscar para ver factores.
          </FeedbackMessage>
        )}
        {consultaRealizada && !cargando && !error && factores.length === 0 && (
          <FeedbackMessage variant="warning">No hay factores para esos filtros.</FeedbackMessage>
        )}

        <div style={{ display: "flex", justifyContent: "center", flexWrap: "wrap", gap: "0.75rem", marginTop: "1rem" }}>
          <Boton texto="Nuevo factor" onClick={abrirAlta} disabled={cargandoCatalogo || competencias.length === 0} />
          <Boton texto="Modificar" onClick={abrirEdicion} disabled={seleccionado === null || cargando} />
          <Boton texto="Eliminar" onClick={() => void eliminar()} disabled={seleccionado === null || cargando} />
          <Link href="/">
            <Boton texto="Inicio" type="button" />
          </Link>
        </div>
      </section>

      {modalAbierto && (
        <div role="dialog" aria-modal="true" aria-labelledby="titulo-form-factor" style={overlayStyle}>
          <section style={{ width: "100%", maxWidth: "600px", maxHeight: "90vh", overflowY: "auto", background: "#F5F5F5", borderRadius: "8px", padding: "1.5rem" }}>
            <h2 id="titulo-form-factor" style={{ textAlign: "center", fontSize: "1.5rem" }}>
              {idEdicion === null ? "Nuevo factor" : "Modificar factor"}
            </h2>
            <form onSubmit={guardar}>
              <ul className="form-field-list">
                <li>
                  <label>
                    Competencia
                    <select
                      value={formulario.id_competencia}
                      onChange={(event) => cambiarCampo("id_competencia", event.target.value)}
                      required
                      style={controlStyle}
                    >
                      <option value="">Seleccionar competencia</option>
                      {competencias.map((competencia) => (
                        <option key={competencia.id} value={String(competencia.id)}>
                          {competencia.codigo} — {competencia.nombre}
                        </option>
                      ))}
                    </select>
                  </label>
                </li>
                <li>
                  <label>
                    Código
                    <input value={formulario.codigo} onChange={(event) => cambiarCampo("codigo", event.target.value)} maxLength={30} required disabled={idEdicion !== null} style={controlStyle} />
                  </label>
                </li>
                <li>
                  <label>
                    Nombre
                    <input value={formulario.nombre} onChange={(event) => cambiarCampo("nombre", event.target.value)} maxLength={50} required style={controlStyle} />
                  </label>
                </li>
                <li>
                  <label>
                    Descripción
                    <textarea value={formulario.descripcion} onChange={(event) => cambiarCampo("descripcion", event.target.value)} minLength={30} maxLength={300} required rows={4} style={controlStyle} />
                  </label>
                </li>
                <li>
                  <label>
                    Número de orden en la competencia
                    <input type="number" min={1} max={32767} step={1} value={formulario.nroOrden} onChange={(event) => cambiarCampo("nroOrden", event.target.value)} required style={controlStyle} />
                  </label>
                </li>
              </ul>
              {error && <FeedbackMessage variant="error">{error}</FeedbackMessage>}
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
