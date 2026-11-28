"use client";

import { useState, type FormEvent } from "react";
import Link from "next/link";
import Boton from "../../componentes/boton";
import Tabla from "../../componentes/tabla";
import FeedbackMessage from "@/componentes/feedback-message";
import {
  backendRequest,
  DEFAULT_DELETE_CONSULTOR_ID,
  getErrorMessage,
  isPositiveLongIdentifier,
  type OpcionRespuestaDto,
  type OpcionRespuestaDtoCreate,
} from "@/lib/backend-api";

type OpcionFormulario = {
  fila: number;
  orden: string;
  descripcion: string;
};

type FormularioOpcionRespuesta = {
  nombre: string;
  descripcion: string;
  opciones: OpcionFormulario[];
};

const crearOpcionesIniciales = (): OpcionFormulario[] => [
  { fila: 1, orden: "1", descripcion: "" },
  { fila: 2, orden: "2", descripcion: "" },
];

const formularioVacio = (): FormularioOpcionRespuesta => ({
  nombre: "",
  descripcion: "",
  opciones: crearOpcionesIniciales(),
});

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

export default function GestionarOpcionesRespuesta() {
  const [opcionesRespuesta, setOpcionesRespuesta] = useState<OpcionRespuestaDto[]>([]);
  const [busqueda, setBusqueda] = useState("");
  const [seleccionada, setSeleccionada] = useState<number | null>(null);
  const [formulario, setFormulario] = useState(formularioVacio);
  const [idEdicion, setIdEdicion] = useState<number | null>(null);
  const [modalAbierto, setModalAbierto] = useState(false);
  const [cargando, setCargando] = useState(false);
  const [consultaRealizada, setConsultaRealizada] = useState(false);
  const [guardando, setGuardando] = useState(false);
  const [error, setError] = useState("");
  const [mensaje, setMensaje] = useState("");

  const consultar = async (filtro = "") => {
    setConsultaRealizada(true);
    setCargando(true);
    setError("");
    setMensaje("");
    try {
      const path = filtro
        ? `/opciones-de-respuesta/nombre/${encodeURIComponent(filtro)}`
        : "/opciones-de-respuesta/lista";
      const resultado = await backendRequest<OpcionRespuestaDto[]>(path);
      setOpcionesRespuesta(resultado);
      setSeleccionada(null);
    } catch (errorSolicitud: unknown) {
      setOpcionesRespuesta([]);
      setSeleccionada(null);
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setCargando(false);
    }
  };

  const refrescarConsulta = async () => {
    if (consultaRealizada) {
      await consultar(busqueda.trim());
      return;
    }

    setOpcionesRespuesta([]);
    setSeleccionada(null);
  };

  const abrirAlta = () => {
    setFormulario(formularioVacio());
    setIdEdicion(null);
    setError("");
    setModalAbierto(true);
  };

  const abrirEdicion = () => {
    const opcionRespuesta = opcionesRespuesta.find((item) => item.id === seleccionada);
    if (!opcionRespuesta) return;
    setFormulario({
      nombre: opcionRespuesta.nombre,
      descripcion: opcionRespuesta.descripcion,
      opciones: opcionRespuesta.opciones.map((opcion) => ({
        fila: opcion.fila,
        orden: String(opcion.orden),
        descripcion: opcion.descripcion,
      })),
    });
    setIdEdicion(opcionRespuesta.id);
    setError("");
    setModalAbierto(true);
  };

  const actualizarOpcion = (
    fila: number,
    campo: "orden" | "descripcion",
    valor: string
  ) => {
    setFormulario((actual) => ({
      ...actual,
      opciones: actual.opciones.map((opcion) =>
        opcion.fila === fila ? { ...opcion, [campo]: valor } : opcion
      ),
    }));
  };

  const agregarOpcion = () => {
    setFormulario((actual) => {
      const siguiente = actual.opciones.length + 1;
      return {
        ...actual,
        opciones: [
          ...actual.opciones,
          { fila: siguiente, orden: String(siguiente), descripcion: "" },
        ],
      };
    });
  };

  const quitarOpcion = (fila: number) => {
    setFormulario((actual) => ({
      ...actual,
      opciones: actual.opciones
        .filter((opcion) => opcion.fila !== fila)
        .map((opcion, index) =>
          idEdicion === null
            ? { ...opcion, fila: index + 1, orden: String(index + 1) }
            : opcion
        ),
    }));
  };

  const guardar = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError("");
    setMensaje("");

    if (!formulario.nombre.trim() || formulario.nombre.length > 50) {
      setError("El nombre es obligatorio y no puede superar 50 caracteres.");
      return;
    }
    if (!formulario.descripcion.trim() || formulario.descripcion.length > 300) {
      setError("La descripción es obligatoria y no puede superar 300 caracteres.");
      return;
    }
    if (formulario.opciones.length < 2) {
      setError("Se requieren al menos dos opciones de respuesta.");
      return;
    }

    const descripcionesNormalizadas = formulario.opciones.map((opcion) =>
      opcion.descripcion.trim().toLocaleLowerCase()
    );
    if (descripcionesNormalizadas.some((descripcion) => !descripcion || descripcion.length > 100)) {
      setError("Cada opción requiere una descripción de hasta 100 caracteres.");
      return;
    }
    if (new Set(descripcionesNormalizadas).size !== descripcionesNormalizadas.length) {
      setError("Las descripciones de las opciones no pueden repetirse.");
      return;
    }

    const ordenes = formulario.opciones.map((opcion) => Number(opcion.orden));
    if (
      ordenes.some(
        (orden) =>
          !Number.isInteger(orden) || orden < 1 || orden > formulario.opciones.length
      ) ||
      new Set(ordenes).size !== ordenes.length
    ) {
      setError(`Los órdenes deben ser únicos y consecutivos del 1 al ${formulario.opciones.length}.`);
      return;
    }

    setGuardando(true);
    try {
      const body: OpcionRespuestaDtoCreate = {
        nombre: formulario.nombre.trim(),
        descripcion: formulario.descripcion.trim(),
        opciones: formulario.opciones.map((opcion) => ({
          fila: opcion.fila,
          orden: Number(opcion.orden),
          descripcion: opcion.descripcion.trim(),
        })),
      };
      const path =
        idEdicion === null
          ? "/opciones-de-respuesta/new"
          : `/opciones-de-respuesta/update/${encodeURIComponent(String(idEdicion))}`;
      const resultado = await backendRequest<OpcionRespuestaDto>(path, {
        method: idEdicion === null ? "POST" : "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });
      setModalAbierto(false);
      await refrescarConsulta();
      setMensaje(
        idEdicion === null
          ? `Opciones de respuesta creadas: ${resultado.nombre}.`
          : `Opciones de respuesta actualizadas: ${resultado.nombre}.`
      );
    } catch (errorSolicitud: unknown) {
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setGuardando(false);
    }
  };

  const eliminar = async () => {
    const opcionRespuesta = opcionesRespuesta.find((item) => item.id === seleccionada);
    if (!opcionRespuesta) return;
    const idConsultorLimpio = DEFAULT_DELETE_CONSULTOR_ID;
    const idOpcionRespuesta = String(opcionRespuesta.id);
    if (
      !isPositiveLongIdentifier(idOpcionRespuesta) ||
      !isPositiveLongIdentifier(idConsultorLimpio)
    ) {
      setError("Los IDs de opción de respuesta y consultor deben ser enteros positivos válidos.");
      return;
    }

    setCargando(true);
    setError("");
    setMensaje("");
    try {
      const respuesta = await backendRequest<string>(
        `/opciones-de-respuesta/delete/${encodeURIComponent(idOpcionRespuesta)}/responsable/${encodeURIComponent(idConsultorLimpio)}`,
        { method: "DELETE" }
      );
      await refrescarConsulta();
      setMensaje(respuesta);
    } catch (errorSolicitud: unknown) {
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setCargando(false);
    }
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
        <h1 style={{ textAlign: "center", fontSize: "2rem" }}>Gestionar opciones de respuesta</h1>
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
          <div style={{ alignSelf: "end", display: "flex", gap: "0.5rem" }}>
            <Boton texto="Buscar" onClick={() => void consultar(busqueda.trim())} disabled={cargando} />
            <Boton
              texto="Limpiar"
              onClick={() => {
                setBusqueda("");
                setOpcionesRespuesta([]);
                setSeleccionada(null);
                setConsultaRealizada(false);
                setError("");
                setMensaje("");
              }}
              disabled={cargando}
            />
          </div>
        </div>

        {cargando && <p role="status">Consultando opciones de respuesta...</p>}
        {error && <FeedbackMessage variant="error">{error}</FeedbackMessage>}
        {mensaje && <FeedbackMessage variant="success">{mensaje}</FeedbackMessage>}

        {consultaRealizada && (
          <Tabla
            columnas={["ID", "Nombre", "Descripción", "Cantidad", "Opciones"]}
            datos={opcionesRespuesta.map((item) => [
              item.id,
              item.nombre,
              item.descripcion,
              item.opciones.length,
              item.opciones
                .slice()
                .sort((a, b) => a.orden - b.orden)
                .map((opcion) => `${opcion.orden}. ${opcion.descripcion}`)
                .join(" | "),
            ])}
            onRowClick={(index) => setSeleccionada(opcionesRespuesta[index]?.id ?? null)}
            seleccionados={opcionesRespuesta.flatMap((item, index) =>
              item.id === seleccionada ? [index] : []
            )}
          />
        )}
        {!consultaRealizada && (
          <FeedbackMessage variant="warning">
            Ingresá un nombre y seleccioná Buscar para ver opciones de respuesta.
          </FeedbackMessage>
        )}
        {consultaRealizada && !cargando && !error && opcionesRespuesta.length === 0 && (
          <FeedbackMessage variant="warning">
            No hay opciones de respuesta para esa búsqueda.
          </FeedbackMessage>
        )}

        <div style={{ display: "flex", justifyContent: "center", flexWrap: "wrap", gap: "0.75rem", marginTop: "1rem" }}>
          <Boton texto="Nueva opción de respuesta" onClick={abrirAlta} />
          <Boton texto="Modificar" onClick={abrirEdicion} disabled={seleccionada === null || cargando} />
          <Boton texto="Eliminar" onClick={() => void eliminar()} disabled={seleccionada === null || cargando} />
          <Link href="/">
            <Boton texto="Inicio" type="button" />
          </Link>
        </div>
      </section>

      {modalAbierto && (
        <div role="dialog" aria-modal="true" aria-labelledby="titulo-form-opciones" style={overlayStyle}>
          <section style={{ width: "100%", maxWidth: "760px", maxHeight: "90vh", overflowY: "auto", background: "#F5F5F5", borderRadius: "8px", padding: "1.5rem" }}>
            <h2 id="titulo-form-opciones" style={{ textAlign: "center", fontSize: "1.5rem" }}>
              {idEdicion === null ? "Nueva opción de respuesta" : "Modificar opción de respuesta"}
            </h2>
            <form onSubmit={guardar}>
              <ul className="form-field-list">
                <li>
                  <label>
                    Nombre
                    <input
                      value={formulario.nombre}
                      onChange={(event) => setFormulario((actual) => ({ ...actual, nombre: event.target.value }))}
                      maxLength={50}
                      required
                      disabled={idEdicion !== null}
                      style={controlStyle}
                    />
                  </label>
                </li>
                <li>
                  <label>
                    Descripción
                    <textarea
                      value={formulario.descripcion}
                      onChange={(event) => setFormulario((actual) => ({ ...actual, descripcion: event.target.value }))}
                      maxLength={300}
                      required
                      rows={3}
                      style={controlStyle}
                    />
                  </label>
                </li>
              </ul>

              <h3>Opciones</h3>
              <p>
                El orden debe ser consecutivo desde 1. Al modificar, el backend no permite cambiar
                la cantidad de opciones ni sus números de fila.
              </p>
              <ul className="form-option-list">
                {formulario.opciones.map((opcion) => (
                  <li key={opcion.fila} className="form-option-item">
                    <label>
                      Orden
                      <input
                        type="number"
                        value={opcion.orden}
                        min={1}
                        max={32767}
                        step={1}
                        onChange={(event) => actualizarOpcion(opcion.fila, "orden", event.target.value)}
                        readOnly={idEdicion !== null}
                        required
                        style={controlStyle}
                      />
                    </label>
                    <label>
                      Descripción
                      <textarea
                        value={opcion.descripcion}
                        onChange={(event) => actualizarOpcion(opcion.fila, "descripcion", event.target.value)}
                        maxLength={100}
                        required
                        rows={2}
                        style={controlStyle}
                      />
                    </label>
                    <button
                      type="button"
                      onClick={() => quitarOpcion(opcion.fila)}
                      disabled={idEdicion !== null || formulario.opciones.length <= 2}
                      style={{ padding: "0.5rem", cursor: idEdicion !== null || formulario.opciones.length <= 2 ? "not-allowed" : "pointer" }}
                    >
                      Quitar
                    </button>
                  </li>
                ))}
              </ul>
              {idEdicion === null && (
                <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "0.75rem" }}>
                  <button type="button" onClick={agregarOpcion}>Agregar opción</button>
                </div>
              )}
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
