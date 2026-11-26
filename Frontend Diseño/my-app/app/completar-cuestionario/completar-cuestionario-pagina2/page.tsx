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

// Datos de muestra
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

const TIEMPO_TOTAL = 600; // 10 minutos en segundos (ajustá el número que quieras)

export default function PreguntasPorFactor() {
  const router = useRouter();

  const [factorActual, setFactorActual] = useState(0);
  const [respuestas, setRespuestas] = useState<Record<number, string>>({});
  const [puntuacionTotal, setPuntuacionTotal] = useState(0);
  const [tiempoRestante, setTiempoRestante] = useState(TIEMPO_TOTAL);
  const [cargado, setCargado] = useState(false);

  // Cargar progreso guardado al montar
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

  // Guardar progreso cada vez que cambia algo
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

  // Temporizador
  useEffect(() => {
    if (!cargado) return;

    const intervalo = setInterval(() => {
      setTiempoRestante((prev) => {
        if (prev <= 1) {
          clearInterval(intervalo);
          localStorage.removeItem("progresoCuestionario");
          router.push("/instrucciones"); // ← cambiá esta ruta por la de tu pantalla de instrucciones
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(intervalo);
  }, [cargado, router]);

  const factor = factoresEjemplo[factorActual];
  const esUltimo = factorActual === factoresEjemplo.length - 1;

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
    if (!esUltimo) {
      setFactorActual(factorActual + 1);
    } else {
      localStorage.removeItem("progresoCuestionario");
      console.log("Puntuación final:", puntuacionTotal);
      console.log("Respuestas:", respuestas);
      // acá después mandás los resultados a la base
      router.push("/instrucciones"); // o a una pantalla de resultados
    }
  };

  const formatearTiempo = (segundos: number) => {
    const m = Math.floor(segundos / 60);
    const s = segundos % 60;
    return `\( {m}: \){s.toString().padStart(2, "0")}`;
  };

  if (!cargado) return null;

  return (
    <div
      style={{
        maxWidth: "700px",
        margin: "2rem auto",
        padding: "2rem",
        border: "2px solid #1E2A1C",
        borderRadius: "8px",
        background: "#F5F5F5",
      }}
    >
      <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "1rem" }}>
        <span>Puntos: {puntuacionTotal}</span>
        <span style={{ fontWeight: "bold", color: tiempoRestante < 60 ? "red" : "#1E2A1C" }}>
          Tiempo: {formatearTiempo(tiempoRestante)}
        </span>
      </div>

      <h2 style={{ textAlign: "center", marginBottom: "0.5rem" }}>
        {factor.nombre}
      </h2>
      <p style={{ textAlign: "center", color: "#555", marginBottom: "2rem" }}>
        Factor {factorActual + 1} de {factoresEjemplo.length}
      </p>

      {factor.preguntas.map((pregunta) => (
        <div key={pregunta.id} style={{ marginBottom: "2rem" }}>
          <p style={{ fontWeight: "bold", marginBottom: "0.8rem" }}>
            {pregunta.texto}
          </p>

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

      <div style={{ textAlign: "center", marginTop: "2rem" }}>
        <Boton
          texto={esUltimo ? "Finalizar" : "Siguiente"}
          onClick={siguiente}
          padding="10px 30px"
        />
      </div>
    </div>
  );
}
