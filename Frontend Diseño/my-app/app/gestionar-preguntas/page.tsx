"use client";
import { useEffect, useState } from "react";
import Boton from "../../componentes/boton";
import Input from "../../componentes/input";
import Input2 from "../../componentes/input2";
import Tabla from "../../componentes/tabla";
import Link from "next/dist/client/link";
import {
  backendRequest,
  getErrorMessage,
  isPositiveLongIdentifier,
  type PreguntaDto,
  type PreguntaDtoCreate,
} from "@/lib/backend-api";

export default function EvaluarCandidato() {
  const [nombrePregunta, setNombrePregunta] = useState("");
  const [factor, setFactor] = useState("");
  const [competencia, setCompetencia] = useState("");

  const [mostrarTablas, setMostrarTablas] = useState(false);
  const [paddingArriba, setPaddingArriba] = useState("17rem");
  const [preguntas, setPreguntas] = useState<PreguntaDto[]>([]);
  const [cargando, setCargando] = useState(false);
  const [errorBusqueda, setErrorBusqueda] = useState("");

  const [seleccionados, setSeleccionados] = useState<number[]>([]);
  const [preguntaEnEdicion, setPreguntaEnEdicion] = useState<PreguntaDto | null>(null);
  const [formularioEdicion, setFormularioEdicion] = useState({
    titulo: "",
    pregunta: "",
    descripcion: "",
    ponderaciones: {} as Record<number, string>,
  });
  const [guardandoEdicion, setGuardandoEdicion] = useState(false);
  const [errorEdicion, setErrorEdicion] = useState("");

  const seleccionDeFilas = (index: number) => {
    if (seleccionados[0] === index) {
      setSeleccionados([]);
    } else {
      setSeleccionados([index]);
    }
  };

  const handleBuscarPregunta = async () => {
    const hayAlMenosUnCampo =
      nombrePregunta.trim() !== "" ||
      factor.trim() !== "" ||
      competencia.trim() !== "";

    if (!hayAlMenosUnCampo) {
      alert("Debes completar al menos un campo del formulario para buscar una pregunta.");
      setMostrarTablas(false);
      return;
    }

    setMostrarTablas(true);
    setPaddingArriba("1rem");
    setCargando(true);
    setErrorBusqueda("");

    const parametros = new URLSearchParams();
    if (competencia.trim()) parametros.set("competencia", competencia.trim());
    if (factor.trim()) parametros.set("factor", factor.trim());
    if (nombrePregunta.trim()) parametros.set("pregunta", nombrePregunta.trim());

    try {
      const resultado = await backendRequest<PreguntaDto[]>(
        `/gestionar-preguntas/buscar/?${parametros.toString()}`
      );
      setPreguntas(resultado);
      setSeleccionados([]);
    } catch (error: unknown) {
      setErrorBusqueda(getErrorMessage(error));
      setPreguntas([]);
    } finally {
      setCargando(false);
    }
  };

  const handleEliminarPregunta = async () => {
    if (seleccionados.length === 0) {
      alert("Debes seleccionar una pregunta de la tabla para eliminarla.");
      return;
    }
    const preguntaSeleccionada = preguntas[seleccionados[0]];
    if (!preguntaSeleccionada) return;
    const idConsultor = window.prompt("Ingresá el ID del consultor responsable de la eliminación.");
    if (!idConsultor?.trim()) return;
    const idPregunta = String(preguntaSeleccionada.id);
    const idConsultorLimpio = idConsultor.trim();
    if (
      !isPositiveLongIdentifier(idPregunta) ||
      !isPositiveLongIdentifier(idConsultorLimpio)
    ) {
      setErrorBusqueda("Los IDs de pregunta y consultor deben ser enteros positivos válidos.");
      return;
    }

    setCargando(true);
    setErrorBusqueda("");
    try {
      await backendRequest<string>(
        `/gestionar-preguntas/delete/${encodeURIComponent(idPregunta)}/responsable/${encodeURIComponent(idConsultorLimpio)}`,
        { method: "DELETE" }
      );
      setPreguntas((actuales) =>
        actuales.filter((pregunta) => pregunta.id !== preguntaSeleccionada.id)
      );
      setSeleccionados([]);
    } catch (errorEliminacion: unknown) {
      setErrorBusqueda(getErrorMessage(errorEliminacion));
    } finally {
      setCargando(false);
    }
  };

  const abrirEdicion = () => {
    const pregunta = preguntas[seleccionados[0]];
    if (!pregunta) return;

    const ponderaciones = Object.fromEntries(
      pregunta.respuesta.opciones.map((opcion) => {
        const ponderacion = pregunta.ponderaciones.find(
          (actual) => actual.opcion === opcion.fila
        );
        return [opcion.fila, String(ponderacion?.puntaje ?? "")];
      })
    );
    setFormularioEdicion({
      titulo: pregunta.nombre,
      pregunta: pregunta.pregunta,
      descripcion: pregunta.descripcion ?? "",
      ponderaciones,
    });
    setPreguntaEnEdicion(pregunta);
    setErrorEdicion("");
  };

  const guardarModificacionPregunta = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!preguntaEnEdicion) return;

    const titulo = formularioEdicion.titulo.trim();
    const textoPregunta = formularioEdicion.pregunta.trim();
    const descripcion = formularioEdicion.descripcion.trim();
    if (!titulo || titulo.length > 80 || !textoPregunta || textoPregunta.length > 150 || descripcion.length > 300) {
      setErrorEdicion("Revisá los campos: título hasta 80, pregunta hasta 150 y descripción hasta 300 caracteres.");
      return;
    }

    const opciones = preguntaEnEdicion.respuesta.opciones;
    const valoresPonderacion = opciones.map((opcion) =>
      Number(formularioEdicion.ponderaciones[opcion.fila])
    );
    if (
      valoresPonderacion.some(
        (valor, index) =>
          formularioEdicion.ponderaciones[opciones[index].fila] === undefined ||
          formularioEdicion.ponderaciones[opciones[index].fila].trim() === "" ||
          !Number.isInteger(valor) ||
          valor < 0
      ) ||
      valoresPonderacion.reduce((total, valor) => total + valor, 0) !== 10
    ) {
      setErrorEdicion("Ingresá una ponderación entera no negativa para cada opción; la suma debe ser 10.");
      return;
    }

    const body: PreguntaDtoCreate = {
      idFactor: preguntaEnEdicion.factor.id,
      titulo,
      pregunta: textoPregunta,
      descripcion,
      id_opcionRespuesta: preguntaEnEdicion.respuesta.id,
      ponderaciones: valoresPonderacion,
    };

    setGuardandoEdicion(true);
    setErrorEdicion("");
    try {
      const actualizada = await backendRequest<PreguntaDto>(
        `/gestionar-preguntas/update/${encodeURIComponent(String(preguntaEnEdicion.id))}`,
        {
          method: "PATCH",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(body),
        }
      );
      setPreguntas((actuales) =>
        actuales.map((item) => (item.id === actualizada.id ? actualizada : item))
      );
      setSeleccionados([]);
      setPreguntaEnEdicion(null);
    } catch (errorSolicitud: unknown) {
      setErrorEdicion(getErrorMessage(errorSolicitud));
    } finally {
      setGuardandoEdicion(false);
    }
  };

  useEffect(() => {
    const ajustarPadding = () => {
      if (window.innerWidth < 1400) {
        setPaddingArriba("4rem");
      } else {
        setPaddingArriba("17rem");
      }
    };
    ajustarPadding();
    window.addEventListener("resize", ajustarPadding);
    return () => window.removeEventListener("resize", ajustarPadding);
  }, []);

  return (
    <div
      style={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "flex-start",
        paddingTop: paddingArriba,
      }}
    >
      <div
        className="flex flex-col flex-1"
        style={{
          display: "flex",
          maxWidth: mostrarTablas ? "1100px" : "800px",
          margin: "2rem auto",
          border: "2px solid #1E2A1C",
          borderRadius: "8px",
          background: "#F5F5F5",
          alignItems: "center",
          width: "100%",
          paddingBottom: "2rem",
        }}
      >
        <h2 style={{ display: "flex", margin: "1rem auto 0.5rem", fontSize: "2rem" }}>
          <img
            src="/lupa.png"
            alt="Lupa"
            style={{
              width: "30px",
              height: "30px",
              objectFit: "contain",
              marginRight: "0.5rem",
              marginTop: "0.5rem",
            }}
          />
          Buscar pregunta
        </h2>

        <hr
          style={{
            width: "100%",
            border: "none",
            borderTop: "2px solid #1E2A1C",
            margin: "0.5rem 0rem 1.5rem 0rem",
            alignSelf: "stretch",
          }}
        />

        <div style={{ marginBottom: "10px" }}>
          <Input2
            label="Nombre de pregunta: "
            placeholder="Nombre de pregunta"
            value={nombrePregunta}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
              setNombrePregunta(e.target.value)
            }
          />
        </div>
        <div style={{ marginBottom: "10px", display: "flex", gap: "2rem" }}>
          <div style={{ flex: "1" }}>
            <Input2
              label="Factor: "
              placeholder="Factor"
              value={factor}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                setFactor(e.target.value)
              }
            />
          </div>
          <div style={{ flex: "1" }}>
            <Input2
              label="Competencia: "
              placeholder="Competencia"
              value={competencia}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                setCompetencia(e.target.value)
              }
            />
          </div>
        </div>

        <div
          style={{
            display: "flex",
            gap: "70px",
            textAlign: "center",
            margin: "1rem 0",
            justifyContent: "center",
          }}
        >
          <Link href="/gestionar-preguntas/dar-alta-pregunta">
            <Boton texto="Nueva pregunta" padding="10px 10px" />
          </Link>
          <Boton
            texto="Buscar pregunta"
            onClick={handleBuscarPregunta}
            padding="10px 10px"
          />
        </div>

        {mostrarTablas && (
          <div style={{ marginTop: "2rem", width: "100%" }}>
            <hr
              style={{
                width: "100%",
                border: "none",
                borderTop: "2px solid #1E2A1C",
                margin: "0.5rem 0rem 1.5rem 0rem",
                alignSelf: "stretch",
              }}
            />
          </div>
        )}

        {mostrarTablas && (
          <div style={{ marginTop: "2rem" }}>
            <div style={{ position: "relative", display: "inline-block" }}>
              <Tabla
                columnas={["Pregunta", "Competencia", "Factor"]}
                datos={preguntas.map((pregunta) => [
                  pregunta.nombre,
                  pregunta.factor.competencia.nombre,
                  pregunta.factor.nombre,
                ])}
                onRowClick={seleccionDeFilas}
                seleccionados={seleccionados}
              />

              <div
                style={{
                  display: "flex",
                  gap: "70px",
                  textAlign: "center",
                  margin: "1rem 0",
                  justifyContent: "center",
                }}
              >
                <Boton
                  texto="Eliminar pregunta"
                  onClick={handleEliminarPregunta}
                  padding="10px 10px"
                  disabled={seleccionados.length === 0 || cargando}
                />
                <Boton
                  texto="Modificar pregunta"
                  onClick={abrirEdicion}
                  padding="10px 10px"
                  disabled={seleccionados.length === 0}
                />
              </div>
            </div>
          </div>
        )}

        {mostrarTablas && cargando && <p role="status">Buscando preguntas...</p>}
        {mostrarTablas && errorBusqueda && (
          <p role="alert">No se pudieron cargar las preguntas: {errorBusqueda}</p>
        )}
        {mostrarTablas && !cargando && !errorBusqueda && preguntas.length === 0 && (
          <p>No hay preguntas que coincidan con los filtros.</p>
        )}
      </div>

      {preguntaEnEdicion && (
        <div
          role="dialog"
          aria-modal="true"
          aria-labelledby="titulo-edicion-pregunta"
          style={{
            position: "fixed",
            inset: 0,
            zIndex: 1000,
            display: "grid",
            placeItems: "center",
            padding: "1rem",
            background: "rgba(0,0,0,0.5)",
          }}
        >
          <section
            style={{
              width: "100%",
              maxWidth: "700px",
              maxHeight: "90vh",
              overflowY: "auto",
              background: "#F5F5F5",
              borderRadius: "8px",
              padding: "1.5rem",
            }}
          >
            <h2 id="titulo-edicion-pregunta" style={{ textAlign: "center" }}>
              Modificar pregunta
            </h2>
            <p>
              Competencia: {preguntaEnEdicion.factor.competencia.nombre} — Factor:{" "}
              {preguntaEnEdicion.factor.nombre}. Estas asociaciones no se pueden modificar.
            </p>
            <p>Configuración de respuesta: {preguntaEnEdicion.respuesta.nombre}</p>
            <form onSubmit={guardarModificacionPregunta}>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Título
                <input
                  value={formularioEdicion.titulo}
                  onChange={(event) =>
                    setFormularioEdicion((actual) => ({
                      ...actual,
                      titulo: event.target.value,
                    }))
                  }
                  maxLength={80}
                  required
                  style={{ width: "100%", padding: "0.6rem" }}
                />
              </label>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Pregunta
                <textarea
                  value={formularioEdicion.pregunta}
                  onChange={(event) =>
                    setFormularioEdicion((actual) => ({
                      ...actual,
                      pregunta: event.target.value,
                    }))
                  }
                  maxLength={150}
                  required
                  rows={3}
                  style={{ width: "100%", padding: "0.6rem" }}
                />
              </label>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Descripción (opcional)
                <textarea
                  value={formularioEdicion.descripcion}
                  onChange={(event) =>
                    setFormularioEdicion((actual) => ({
                      ...actual,
                      descripcion: event.target.value,
                    }))
                  }
                  maxLength={300}
                  rows={3}
                  style={{ width: "100%", padding: "0.6rem" }}
                />
              </label>
              <h3>Ponderaciones de las opciones</h3>
              {preguntaEnEdicion.respuesta.opciones.map((opcion) => (
                <label key={opcion.fila} style={{ display: "block", margin: "0.75rem 0" }}>
                  {opcion.descripcion}
                  <input
                    type="number"
                    min="0"
                    step="1"
                    value={formularioEdicion.ponderaciones[opcion.fila] ?? ""}
                    onChange={(event) =>
                      setFormularioEdicion((actual) => ({
                        ...actual,
                        ponderaciones: {
                          ...actual.ponderaciones,
                          [opcion.fila]: event.target.value,
                        },
                      }))
                    }
                    required
                    style={{ display: "block", width: "100%", padding: "0.6rem" }}
                  />
                </label>
              ))}
              {errorEdicion && <p role="alert">{errorEdicion}</p>}
              <div style={{ display: "flex", justifyContent: "center", gap: "0.75rem", marginTop: "1rem" }}>
                <Boton
                  texto={guardandoEdicion ? "Guardando..." : "Guardar cambios"}
                  type="submit"
                  disabled={guardandoEdicion}
                />
                <Boton
                  texto="Cancelar"
                  type="button"
                  onClick={() => setPreguntaEnEdicion(null)}
                  disabled={guardandoEdicion}
                />
              </div>
            </form>
          </section>
        </div>
      )}
    </div>
  );
}