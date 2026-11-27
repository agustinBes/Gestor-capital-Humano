"use client";
import { useState, useEffect } from "react";
import Boton from "../../../componentes/boton";
import Link from "next/link";
import InputMayus from "../../../componentes/inputMayus";
import { backendRequest, getErrorMessage } from "@/lib/backend-api";

export default function CompletarCuestionario() {
  const [competencia, setCompetencia] = useState("");
  const [factor, setFactor] = useState("");
  const [opcionRespuesta, setOpcionRespuesta] = useState("");
  const [factores, setFactores] = useState([]);
  const [opcionesRespuesta, setOpcionesRespuesta] = useState([]);
  const [ponderaciones, setPonderaciones] = useState({});
  const [cargandoCatalogos, setCargandoCatalogos] = useState(true);
  const [errorCatalogos, setErrorCatalogos] = useState("");

  const [formData, setFormData] = useState({
    nombrePregunta: "",
    pregunta: "",
    descripcion: "",
  });

  const [errores, setErrores] = useState([]);
  const [paddingArriba, setPaddingArriba] = useState("12rem");
  const [guardando, setGuardando] = useState(false);
  const [preguntaCreada, setPreguntaCreada] = useState(null);
  const competencias = Array.from(
    new Map(factores.map((item) => [item.competencia.id, item.competencia])).values()
  );
  const factoresDeCompetencia = factores.filter(
    (item) => String(item.competencia.id) === competencia
  );

  useEffect(() => {
    let activo = true;
    Promise.all([
      backendRequest("/factores/lista"),
      backendRequest("/opciones-de-respuesta/nombre/"),
    ])
      .then(([resultadoFactores, resultadoOpciones]) => {
        if (activo) {
          setFactores(resultadoFactores);
          setOpcionesRespuesta(resultadoOpciones);
        }
      })
      .catch((errorSolicitud) => {
        if (activo) setErrorCatalogos(getErrorMessage(errorSolicitud));
      })
      .finally(() => {
        if (activo) setCargandoCatalogos(false);
      });

    return () => {
      activo = false;
    };
  }, []);

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

  const handleAceptar = async () => {
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
    if (formData.nombrePregunta.length > 80) {
      omisiones.push("El nombre de pregunta no puede superar 80 caracteres.");
    }
    if (formData.pregunta.length > 150) {
      omisiones.push("La pregunta no puede superar 150 caracteres.");
    }
    if (formData.descripcion.length > 300) {
      omisiones.push("La descripción no puede superar 300 caracteres.");
    }
    if (omisiones.length > 0) {
      setErrores(omisiones);
      return;
    }

    const respuestaSeleccionada = opcionesRespuesta.find(
      (item) => String(item.id) === opcionRespuesta
    );
    if (!respuestaSeleccionada || respuestaSeleccionada.opciones.length < 2) {
      setErrores(["La opción seleccionada no tiene las opciones requeridas por el backend."]);
      return;
    }

    const ponderacionesIncompletas = respuestaSeleccionada.opciones.some(
      (opcion) => ponderaciones[opcion.fila]?.trim() === ""
    );
    const valoresPonderacion = respuestaSeleccionada.opciones.map((opcion) =>
      Number(ponderaciones[opcion.fila])
    );
    if (
      ponderacionesIncompletas ||
      valoresPonderacion.some(
        (valor, index) =>
          ponderaciones[respuestaSeleccionada.opciones[index].fila] === undefined ||
          !Number.isInteger(valor) ||
          valor < 0
      )
    ) {
      setErrores(["Ingresá una ponderación entera no negativa para cada opción de respuesta."]);
      return;
    }
    if (valoresPonderacion.reduce((total, valor) => total + valor, 0) !== 10) {
      setErrores(["La suma de las ponderaciones debe ser 10."]);
      return;
    }

    setErrores([]);
    setGuardando(true);
    try {
      const resultado = await backendRequest("/gestionar-preguntas/new", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idFactor: Number(factor),
          titulo: formData.nombrePregunta.trim(),
          pregunta: formData.pregunta.trim(),
          descripcion: formData.descripcion.trim(),
          id_opcionRespuesta: Number(opcionRespuesta),
          ponderaciones: valoresPonderacion,
        }),
      });
      setPreguntaCreada(resultado);
    } catch (errorSolicitud) {
      setErrores([getErrorMessage(errorSolicitud)]);
    } finally {
      setGuardando(false);
    }
  };

  const limpiarFormulario = () => {
    setCompetencia("");
    setFactor("");
    setOpcionRespuesta("");
    setPonderaciones({});
    setFormData({ nombrePregunta: "", pregunta: "", descripcion: "" });
    setPreguntaCreada(null);
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
                <label style={{ display: "block" }}>
                  Competencia:
                  <select
                    value={competencia}
                    onChange={(event) => {
                      setCompetencia(event.target.value);
                      setFactor("");
                    }}
                  >
                    <option value="">Seleccionar competencia</option>
                    {competencias.map((item) => (
                      <option key={item.id} value={item.id}>{item.nombre}</option>
                    ))}
                  </select>
                </label>

                <label style={{ display: "block" }}>
                  Factor:
                  <select value={factor} onChange={(event) => setFactor(event.target.value)}>
                    <option value="">Seleccionar factor</option>
                    {factoresDeCompetencia.map((item) => (
                      <option key={item.id} value={item.id}>{item.nombre}</option>
                    ))}
                  </select>
                </label>
              </div>

              <div>
                <label style={{ display: "block" }}>
                  Opción de respuesta:
                  <select
                    value={opcionRespuesta}
                    onChange={(event) => {
                      setOpcionRespuesta(event.target.value);
                      setPonderaciones({});
                    }}
                  >
                    <option value="">Seleccionar opciones</option>
                    {opcionesRespuesta.map((item) => (
                      <option key={item.id} value={item.id}>{item.nombre}</option>
                    ))}
                  </select>
                </label>

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
            {opcionesRespuesta
              .find((item) => String(item.id) === opcionRespuesta)
              ?.opciones.map((opcion) => (
                <label key={opcion.fila} style={{ display: "block", margin: "0.5rem 1rem" }}>
                  Ponderación para “{opcion.descripcion}”:
                  <input
                    type="number"
                    min="0"
                    step="1"
                    value={ponderaciones[opcion.fila] ?? ""}
                    onChange={(event) =>
                      setPonderaciones((actuales) => ({
                        ...actuales,
                        [opcion.fila]: event.target.value,
                      }))
                    }
                  />
                </label>
              ))}
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
              onClick={() => void handleAceptar()}
              disabled={guardando || cargandoCatalogos || Boolean(errorCatalogos)}
              padding="10px 20px"
            />
            <Link href="/gestionar-preguntas">
              <Boton texto="Cancelar" padding="10px 20px" />
            </Link>
          </div>

          {cargandoCatalogos && <p role="status">Cargando opciones desde el backend...</p>}
          {errorCatalogos && <p role="alert">No se pudieron cargar las opciones: {errorCatalogos}</p>}
          {preguntaCreada && (
            <div role="status">
              <p>Pregunta creada desde el backend: {preguntaCreada.nombre}</p>
              <Boton texto="Crear otra" onClick={limpiarFormulario} />
            </div>
          )}

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

    </div>
  );
}
