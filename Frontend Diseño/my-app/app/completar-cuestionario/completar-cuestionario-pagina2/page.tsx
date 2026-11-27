"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Boton from "../../../componentes/boton";

type Opcion = {
  texto: string;
  puntos: number;
};

type Pregunta = {
  id: number;
  texto: string;
  opciones: Opcion[];
};

type Factor = {
  id: number;
  nombre: string;
  preguntas: Pregunta[];
};

const factoresEjemplo: Factor[] = [
  {
    id: 1,
    nombre: "Liderazgo",
    preguntas: [
      {
        id: 101,
        texto: "¿Cómo reaccionás cuando un compañero no cumple con lo acordado?",
        opciones: [
          { texto: "Lo enfrento directamente", puntos: 3 },
          { texto: "Espero a ver si mejora", puntos: 1 },
          { texto: "Se lo digo al jefe", puntos: 2 },
        ],
      },
      {
        id: 102,
        texto: "¿Preferís tomar decisiones solo o consultar al equipo?",
        opciones: [
          { texto: "Solo", puntos: 1 },
          { texto: "Consultar", puntos: 3 },
          { texto: "Depende de la situación", puntos: 2 },
        ],
      },
    ],
  },
  {
    id: 2,
    nombre: "Trabajo en equipo",
    preguntas: [
      {
        id: 201,
        texto: "¿Qué tan cómodo te sentís trabajando con personas de otras áreas?",
        opciones: [
          { texto: "Muy cómodo", puntos: 3 },
          { texto: "Más o menos", puntos: 2 },
          { texto: "Prefiero mi equipo", puntos: 1 },
        ],
      },
      {
        id: 202,
        texto: "Si hay un conflicto en el grupo, ¿qué hacés?",
        opciones: [
          { texto: "Trato de mediar", puntos: 3 },
          { texto: "Me quedo callado", puntos: 1 },
          { texto: "Tomo partido", puntos: 2 },
        ],
      },
    ],
  },
  {
    id: 3,
    nombre: "Adaptabilidad",
    preguntas: [
      {
        id: 301,
        texto: "¿Cómo te va cuando cambian las prioridades de un día para el otro?",
        opciones: [
          { texto: "Me adapto rápido", puntos: 3 },
          { texto: "Me cuesta un poco", puntos: 2 },
          { texto: "Me estresa mucho", puntos: 1 },
        ],
      },
    ],
  },
];

const TIEMPO_TOTAL = 600;

export default function PreguntasPorFactor() {
  const router = useRouter();
  const [paddingArriba, setPaddingArriba] = useState("9rem");
  const [factorActual, setFactorActual] = useState(0);
  const [respuestas, setRespuestas] = useState<Record<number, string>>({});
  const [puntuacionTotal, setPuntuacionTotal] = useState(0);
  const [tiempoRestante, setTiempoRestante] = useState(TIEMPO_TOTAL);
  const [cargado, setCargado] = useState(false);
  const [errorBloque, setErrorBloque] = useState(false);

  useEffect(() => {
    const guardado = localStorage.getItem("progresoCuestionario");
    if (guardado) {
      const data = JSON.parse(guardado);
      setFactorActual(data.factorActual || 0);
      setRespuestas(data.respuestas || {});
      setPuntuacionTotal(data.puntuacionTotal || 0);
      setTiempoRestante(data.tiempoRestante || TIEMPO_TOTAL);
    }
    setCargado(true);
  }, []);

  useEffect(() => {
    if (!cargado) return;
    const data = {
      factorActual,
      respuestas,
      puntuacionTotal,
      tiempoRestante,
    };
    localStorage.setItem("progresoCuestionario", JSON.stringify(data));
  }, [factorActual, respuestas, puntuacionTotal, tiempoRestante, cargado]);

  useEffect(() => {
    if (!cargado) return;

    const intervalo = setInterval(() => {
      setTiempoRestante((prev) => {
        if (prev <= 1) {
          clearInterval(intervalo);
          localStorage.removeItem("progresoCuestionario");
          router.push("/completar-cuestionario");
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(intervalo);
  }, [cargado, router]);

  const factor = factoresEjemplo[factorActual];
  const esUltimo = factorActual === factoresEjemplo.length - 1;

  const todasRespondidas = factor.preguntas.every(
    (pregunta) =>
      respuestas[pregunta.id] !== undefined &&
      respuestas[pregunta.id] !== null &&
      respuestas[pregunta.id] !== ""
  );

  useEffect(() => {
    if (todasRespondidas && errorBloque) {
      setErrorBloque(false);
    }
  }, [todasRespondidas, errorBloque]);

  const manejarRespuesta = (preguntaId: number, valor: string, puntos: number) => {
    const respuestaAnterior = respuestas[preguntaId];
    let puntosAnteriores = 0;

    if (respuestaAnterior) {
      const pregunta = factor.preguntas.find((p) => p.id === preguntaId);
      const opcionAnterior = pregunta?.opciones.find((o) => o.texto === respuestaAnterior);
      puntosAnteriores = opcionAnterior?.puntos || 0;
    }

    setRespuestas((prev) => ({ ...prev, [preguntaId]: valor }));
    setPuntuacionTotal((prev) => prev - puntosAnteriores + puntos);
  };

  const siguiente = () => {
    if (!todasRespondidas) {
      setErrorBloque(true);
      return;
    }

    if (!esUltimo) {
      setFactorActual(factorActual + 1);
      window.scrollTo({ top: 0, behavior: "smooth" });
    } else {
      localStorage.removeItem("progresoCuestionario");
      console.log("Puntuación final:", puntuacionTotal);
      console.log("Respuestas:", respuestas);
      router.push("/completar-cuestionario");
    }
  };

  const formatearTiempo = (segundos: number) => {
    const m = Math.floor(segundos / 60);
    const s = segundos % 60;
    return `${m}:${s.toString().padStart(2, "0")}`;
  };

  useEffect(() => {
    const ajustarPadding = () => {
      if (window.innerWidth < 1400) {
        setPaddingArriba("1rem");
      } else {
        setPaddingArriba("9rem");
      }
    };
    ajustarPadding();
    window.addEventListener("resize", ajustarPadding);
    return () => window.removeEventListener("resize", ajustarPadding);
  }, []);

  if (!cargado) return null;

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
          Cuestionario de Candidato
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
        <div style={{ position: "relative", width: "100%", textAlign: "center", marginBottom: "1.5rem" }}>
          <p style={{ color: "#555", marginBottom: "0.5rem", marginTop: "0", fontSize: "1.1rem" }}>
            Factor {factorActual + 1} de {factoresEjemplo.length}
          </p>

          <div
            style={{
              position: "absolute",
              right: "30px",
              top: "50%",
              transform: "translateY(-50%)",
              background: "#f8f9fa",
              border: "1px solid #e2e8f0",
              padding: "6px 12px",
              borderRadius: "8px",
              boxShadow: "0 1px 2px rgba(0,0,0,0.04)",
            }}
          >
            <span style={{ fontWeight: "bold", fontSize: "0.9rem", color: tiempoRestante < 60 ? "red" : "#1E2A1C" }}>
              ⏱️ {formatearTiempo(tiempoRestante)}
            </span>
          </div>
        </div>

        {factor.preguntas.map((pregunta) => (
          <div key={pregunta.id} style={{ marginBottom: "2rem", width: "85%", textAlign: "left" }}>
            <p style={{ fontWeight: "bold", marginBottom: "0.8rem" }}>{pregunta.texto}</p>

            {pregunta.opciones.map((opcion) => (
              <label
                key={opcion.texto}
                style={{ display: "block", marginBottom: "0.5rem", cursor: "pointer" }}
              >
                <input
                  type="radio"
                  name={`pregunta-${pregunta.id}`}
                  value={opcion.texto}
                  checked={respuestas[pregunta.id] === opcion.texto}
                  onChange={() => manejarRespuesta(pregunta.id, opcion.texto, opcion.puntos)}
                />{" "}
                {opcion.texto}
              </label>
            ))}
          </div>
        ))}

        <div style={{ textAlign: "center", marginTop: "1rem" }}>
          <Boton
            texto={esUltimo ? "Finalizar" : "Siguiente"}
            onClick={siguiente}
            padding="10px 30px"
          />

          {errorBloque && (
            <p style={{ color: "#d9534f", fontSize: "0.9rem", marginTop: "10px", fontWeight: "500" }}>
              ⚠️ Tenés que responder todas las preguntas de este bloque para continuar.
            </p>
          )}
        </div>
      </div>
    </div>
  );
}