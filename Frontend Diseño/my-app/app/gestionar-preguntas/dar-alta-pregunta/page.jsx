"use client";
import { useState, useEffect } from "react";
import Boton from "../../../componentes/boton";
import Link from "next/dist/client/link";
import InputMayus from "../../../componentes/inputMayus";
import Desplegable from "../../../componentes/desplegable";

export default function CompletarCuestionario() {
  const opciones = ["Consultor", "Analista", "Desarrollador", "Gerente"];

  const [competencia, setCompetencia] = useState("");
  const [factor, setFactor] = useState("");
  const [opcionRespuesta, setOpcionRespuesta] = useState("");

  const [formData, setFormData] = useState({
    nombrePregunta: "",
    pregunta: "",
    descripcion: "",
  });

  const [errores, setErrores] = useState([]);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [paddingArriba, setPaddingArriba] = useState("12rem");

  useEffect(() => {
    const ajustarPadding = () => {
      if (window.innerWidth < 1400) {
        setPaddingArriba("1rem");
      } else {
        setPaddingArriba("12rem");
      }
    };
    ajustarPadding();
    window.addEventListener("resize", ajustarPadding);
    return () => window.removeEventListener("resize", ajustarPadding);
  }, []);

  const handleChange = (campo) => (e) => {
    const valorEnMayusculas = e.target.value.toUpperCase();
    setFormData((prev) => ({
      ...prev,
      [campo]: valorEnMayusculas,
    }));
  };

  const handleAceptar = () => {
    const omisiones = [];

    if (!competencia.trim()) {
      omisiones.push("Falta seleccionar Competencia.");
    }
    if (!factor.trim()) {
      omisiones.push("Falta seleccionar Factor.");
    }
    if (!opcionRespuesta.trim()) {
      omisiones.push("Falta seleccionar Opcion de Respuesta.");
    }
    if (!formData.nombrePregunta.trim()) {
      omisiones.push("Falta ingresar Nombre de pregunta.");
    }
    if (!formData.pregunta.trim()) {
      omisiones.push("Falta ingresar Pregunta.");
    }
    if (omisiones.length > 0) {
      setErrores(omisiones);
      setMostrarModal(false);
      return;
    }

    setErrores([]);
    setMostrarModal(true);
  };

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
          maxWidth: "800px",
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
            src="/cuaderno.png"
            alt="Lupa"
            style={{
              width: "30px",
              height: "30px",
              objectFit: "contain",
              marginRight: "0.5rem",
              marginTop: "0.5rem",
            }}
          />
          Dar de alta pregunta <br />
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

        <div id="formulario">
          <form onSubmit={(e) => e.preventDefault()}>
            <div
              style={{
                display: "flex",
                gap: "30px",
                textAlign: "center",
                margin: "1rem 0",
                justifyContent: "center",
              }}
            >
              <div>
                <Desplegable
                  label="Competencia: "
                  opciones={opciones}
                  valor={competencia}
                  onChange={setCompetencia}
                />

                <Desplegable
                  label="Factor:"
                  opciones={opciones}
                  valor={factor}
                  onChange={setFactor}
                />
              </div>

              <div>
                <Desplegable
                  label="Opcion de Respuesta:"
                  opciones={opciones}
                  valor={opcionRespuesta}
                  onChange={setOpcionRespuesta}
                />

                <InputMayus
                  label="Nombre de pregunta:   "
                  placeholder="Nombre de pregunta"
                  value={formData.nombrePregunta}
                  onChange={handleChange("nombrePregunta")}
                />
              </div>

              <br />

              <div>
                <InputMayus
                  label="Pregunta:   "
                  placeholder="Pregunta"
                  value={formData.pregunta}
                  onChange={handleChange("pregunta")}
                />
                <br />
                <InputMayus
                  label="Descripcion:   "
                  placeholder="Descripcion (Opcional)"
                  value={formData.descripcion}
                  onChange={handleChange("descripcion")}
                />
              </div>
            </div>
          </form>

          {/* Tres botones */}
          <div
            style={{
              display: "flex",
              gap: "30px",
              textAlign: "center",
              margin: "1rem 0",
              justifyContent: "center",
            }}
          >
            <Boton
              type="button"
              texto="Generar con IA"
              onClick={() => alert("CU no implementado")}
              padding="10px 20px"
            />
            <Boton
              type="button"
              texto="Aceptar"
              onClick={handleAceptar}
              padding="10px 20px"
            />
            <Link href="/gestionar-preguntas">
              <Boton texto="Cancelar" padding="10px 20px" />
            </Link>
          </div>

          {/* Mensaje de error en texto plano, color rojo y pequeño borde debajo de los tres botones */}
          {errores.length > 0 && (
            <div
              role="alert"
              style={{
                color: "red",
                border: "1px solid red",
                borderRadius: "4px",
                padding: "0.5rem 0.75rem",
                marginTop: "0.75rem",
                maxWidth: "650px",
                textAlign: "left",
              }}
            >
              {errores.map((error, index) => (
                <div key={index}>{error}</div>
              ))}
            </div>
          )}
        </div>
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
              La Pregunta {formData.nombrePregunta} se ha creado correctamente ¿Desea cargar otra?
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
                <Boton texto="NO" onClick={() => setMostrarModal(false)} />
              </Link>
              <Link href="/gestionar-preguntas/dar-alta-pregunta">
                <Boton texto="SI" onClick={() => setMostrarModal(false)} />
              </Link>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
