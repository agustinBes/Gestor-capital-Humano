"use client";
import { useEffect, useState } from "react";
import Boton from "../../componentes/boton";
import Input from "../../componentes/input";
import Input2 from "../../componentes/input2";
import Tabla from "../../componentes/tabla";
import Link from "next/dist/client/link";
import { backendRequest, getErrorMessage, type PreguntaDto } from "@/lib/backend-api";

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

    setCargando(true);
    setErrorBusqueda("");
    try {
      await backendRequest<string>(
        `/gestionar-preguntas/delete/${preguntaSeleccionada.id}/responsable/${encodeURIComponent(idConsultor.trim())}`,
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
                  onClick={() => alert("CU no implementado")}
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

    </div>
  );
}