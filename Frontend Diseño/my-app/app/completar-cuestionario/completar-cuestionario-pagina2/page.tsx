"use client";

import { Suspense, useEffect, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import Boton from "../../../componentes/boton";
import {
  AUTHENTICATED_QUESTIONNAIRE_SESSION_KEY,
  backendRequest,
  getErrorMessage,
  type BloqueDto,
  type CuestionarioDto,
} from "@/lib/backend-api";

function ResponderBloque() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const idCuestionario = searchParams.get("idCuestionario") ?? "";
  const [resultado, setResultado] = useState<{
    id: string;
    cuestionario: CuestionarioDto | null;
    error: string;
    ready: boolean;
  }>({ id: "", cuestionario: null, error: "", ready: false });
  const [respuestas, setRespuestas] = useState<Record<number, number>>({});
  const [guardando, setGuardando] = useState(false);
  const [errorGuardado, setErrorGuardado] = useState("");

  useEffect(() => {
    let activo = true;
    Promise.resolve()
      .then(() => {
        if (!activo) return;
        if (!idCuestionario) {
          setResultado({
            id: "",
            cuestionario: null,
            error: "Falta el identificador del cuestionario.",
            ready: true,
          });
          return;
        }

        const datosGuardados = sessionStorage.getItem(
          AUTHENTICATED_QUESTIONNAIRE_SESSION_KEY
        );
        if (!datosGuardados) {
          throw new Error("Volvé a autenticarte para acceder a tu cuestionario.");
        }

        const cuestionario = JSON.parse(datosGuardados) as CuestionarioDto;
        if (
          String(cuestionario.idCuestionario) !== idCuestionario ||
          !Array.isArray(cuestionario.bloques)
        ) {
          throw new Error("La sesión no contiene el cuestionario solicitado. Volvé a autenticarte.");
        }

        setResultado({
          id: idCuestionario,
          cuestionario,
          error: "",
          ready: true,
        });
      })
      .catch((errorSolicitud: unknown) => {
        if (activo) {
          setResultado({
            id: idCuestionario,
            cuestionario: null,
            error: getErrorMessage(errorSolicitud),
            ready: true,
          });
        }
      });

    return () => {
      activo = false;
    };
  }, [idCuestionario]);

  const cargando = Boolean(idCuestionario) && (!resultado.ready || resultado.id !== idCuestionario);
  const cuestionario = cargando ? null : resultado.cuestionario;
  const error = !idCuestionario
    ? "Falta el identificador del cuestionario."
    : cargando
      ? ""
      : resultado.error;
  const bloqueActual: BloqueDto | undefined = cuestionario?.bloques.find(
    (bloque) => !bloque.completado
  );
  const bloquesCompletados =
    cuestionario?.bloques.filter((bloque) => bloque.completado).length ?? 0;

  const guardarBloque = async () => {
    if (!cuestionario || !bloqueActual) return;
    const items: { idpregunta: number; NroBloque: number; opcion: number }[] = [];

    for (const item of bloqueActual.items) {
      const opcion = respuestas[item.idPregunta] ?? item.opcionSeleccionada;
      if (opcion === null || opcion === undefined) {
        setErrorGuardado("Respondé todas las preguntas del bloque antes de continuar.");
        return;
      }
      items.push({
        idpregunta: item.idPregunta,
        NroBloque: bloqueActual.numero,
        opcion,
      });
    }

    setGuardando(true);
    setErrorGuardado("");
    try {
      const actualizado = await backendRequest<CuestionarioDto>(
        `/cuestionario/${encodeURIComponent(idCuestionario)}/editar`,
        {
          method: "PATCH",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            id_candidato: cuestionario.idCandidato,
            id_puesto: cuestionario.idPuesto,
            items,
          }),
        }
      );
      setResultado({
        id: idCuestionario,
        cuestionario: actualizado,
        error: "",
        ready: true,
      });
      setRespuestas({});

      const quedanPendientes = actualizado.bloques.some((bloque) => !bloque.completado);
      if (quedanPendientes) {
        sessionStorage.setItem(
          AUTHENTICATED_QUESTIONNAIRE_SESSION_KEY,
          JSON.stringify(actualizado)
        );
      } else {
        sessionStorage.removeItem(AUTHENTICATED_QUESTIONNAIRE_SESSION_KEY);
      }
    } catch (errorSolicitud: unknown) {
      setErrorGuardado(getErrorMessage(errorSolicitud));
    } finally {
      setGuardando(false);
    }
  };

  return (
    <main style={{ minHeight: "100vh", padding: "4rem 1rem" }}>
      <section
        style={{
          maxWidth: "900px",
          margin: "0 auto",
          border: "2px solid #1E2A1C",
          borderRadius: "8px",
          background: "#F5F5F5",
          padding: "1rem",
        }}
      >
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Cuestionario de candidato</h2>
        {cargando && <p role="status">Cargando cuestionario...</p>}
        {error && <p role="alert">No se pudo cargar el cuestionario: {error}</p>}
        {error && (
          <div style={{ display: "flex", justifyContent: "center", margin: "1rem" }}>
            <Boton
              texto="Volver a autenticarme"
              onClick={() => router.push("/completar-cuestionario")}
            />
          </div>
        )}

        {cuestionario && !bloqueActual && (
          <>
            <p>Todos los bloques de este cuestionario están completos.</p>
            <div style={{ display: "flex", justifyContent: "center", margin: "1rem" }}>
              <Boton
                texto="Autenticar otro candidato"
                onClick={() => router.push("/completar-cuestionario")}
              />
            </div>
          </>
        )}

        {cuestionario && bloqueActual && (
          <>
            <p>
              Cuestionario {cuestionario.idCuestionario} — candidato {cuestionario.idCandidato} —
              puesto {cuestionario.idPuesto}
            </p>
            <p>
              Bloque {bloquesCompletados + 1} de {cuestionario.bloques.length}
            </p>
            {bloqueActual.items.map((item) => (
              <fieldset
                key={item.idPregunta}
                style={{ border: "1px solid #1E2A1C", margin: "1rem 0", padding: "1rem" }}
              >
                <legend>{item.titulo}</legend>
                <p>{item.pregunta}</p>
                {item.descripcion && <p>{item.descripcion}</p>}
                {item.opciones.map((opcion) => (
                  <label key={opcion.fila} style={{ display: "block", margin: "0.5rem 0" }}>
                    <input
                      type="radio"
                      name={`pregunta-${item.idPregunta}`}
                      value={opcion.fila}
                      checked={
                        (respuestas[item.idPregunta] ?? item.opcionSeleccionada) === opcion.fila
                      }
                      onChange={() =>
                        setRespuestas((actuales) => ({
                          ...actuales,
                          [item.idPregunta]: opcion.fila,
                        }))
                      }
                    />{" "}
                    {opcion.descripcion}
                  </label>
                ))}
              </fieldset>
            ))}
            {errorGuardado && <p role="alert">{errorGuardado}</p>}
            <div style={{ display: "flex", justifyContent: "center", margin: "1rem" }}>
              <Boton
                texto={guardando ? "Guardando..." : "Guardar bloque"}
                onClick={guardarBloque}
                disabled={guardando}
              />
            </div>
          </>
        )}
      </section>
    </main>
  );
}

export default function CompletarCuestionarioPagina2() {
  return (
    <Suspense fallback={<p role="status">Cargando cuestionario...</p>}>
      <ResponderBloque />
    </Suspense>
  );
}
