"use client";
import { useEffect, useState } from "react";
import Boton from "../../componentes/boton";
import Input from "../../componentes/input";
import Input2 from "../../componentes/input2";
import Tabla from "../../componentes/tabla";
import Link from "next/dist/client/link";

export default function EvaluarCandidato() {
  const [nombrePregunta, setNombrePregunta] = useState("");
  const [factor, setFactor] = useState("");
  const [competencia, setCompetencia] = useState("");

  const [mostrarTablas, setMostrarTablas] = useState(false);
  const [paddingArriba, setPaddingArriba] = useState("17rem");
  const [candidatos, setCandidatos] = useState([
    ["038", "Lucas", "Besigniano"],
    ["069", "Agustina", "Luna"],
    ["016", "Micalea", "Gaitan"],
    ["067", "Ivan", "Cabañas"],
  ]);

  const [mostrarModal, setMostrarModal] = useState(false);
  const [mostrarModal2, setMostrarModal2] = useState(false);
  const [aEvaluar, setAEvaluar] = useState<(string | number)[][]>([]);
  const [seleccionados, setSeleccionados] = useState<number[]>([]);

  const seleccionDeFilas = (index: number) => {
    if (seleccionados[0] === index) {
      setSeleccionados([]);
    } else {
      setSeleccionados([index]);
    }
  };

  const agregarSeleccionados = () => {
    const nuevos = seleccionados.map((i) => candidatos[i]);
    setAEvaluar([...aEvaluar, ...nuevos]);
    setSeleccionados([]);
  };

  const handleBuscarPregunta = () => {
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
  };

  const handleEliminarPregunta = () => {
    if (seleccionados.length === 0) {
      alert("Debes seleccionar una pregunta de la tabla para eliminarla.");
      return;
    }
    setMostrarModal(true);
  };

  const filaSeleccionada =
    seleccionados.length > 0 ? candidatos[seleccionados[0]] : undefined;
  const nombrePreguntaSeleccionada = filaSeleccionada ? String(filaSeleccionada[0]) : "";

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
                datos={candidatos}
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
                />
                <Boton
                  texto="Modificar pregunta"
                  onClick={() => alert("CU no implementado")}
                  padding="10px 10px"
                />
              </div>
            </div>
          </div>
        )}
      </div>

      {mostrarModal && (
        <div
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100vw",
            height: "100vh",
            background: "rgba(0,0,0,0.5)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            zIndex: 1000,
          }}
        >
          <div
            style={{
              background: "#f1f1f1",
              padding: "2rem",
              border: "1px solid #333",
              borderRadius: "4px",
              textAlign: "center",
              maxWidth: "400px",
            }}
          >
            <p>
              Los datos de la pregunta {nombrePreguntaSeleccionada} serán eliminados del
              sistema
            </p>
            <div
              style={{
                display: "flex",
                gap: "1rem",
                justifyContent: "center",
                marginTop: "1.5rem",
              }}
            >
              <Boton texto="ELIMINAR" onClick={() => setMostrarModal2(true)} />
              <Link href="/">
                <Boton texto="CANCELAR" onClick={() => setMostrarModal(false)} />
              </Link>
            </div>
          </div>
        </div>
      )}

      {mostrarModal2 && (
        <div
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100vw",
            height: "100vh",
            background: "rgba(0,0,0,0.5)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            zIndex: 1000,
          }}
        >
          <div
            style={{
              background: "#f1f1f1",
              padding: "2rem",
              border: "1px solid #333",
              borderRadius: "4px",
              textAlign: "center",
              maxWidth: "400px",
            }}
          >
            <p>
              Los datos de la pregunta {nombrePreguntaSeleccionada} han sido eliminados del
              sistema
            </p>
            <div
              style={{
                display: "flex",
                gap: "1rem",
                justifyContent: "center",
                marginTop: "1.5rem",
              }}
            >
              <Link href="/">
                <Boton texto="OK" />
              </Link>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}