"use client";

import { Suspense, useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import Boton from "../../../componentes/boton";
import Tabla from "../../../componentes/tabla";
import FeedbackMessage from "@/componentes/feedback-message";
import {
  backendRequest,
  DEFAULT_DELETE_CONSULTOR_ID,
  getErrorMessage,
  isPositiveLongIdentifier,
  type CompetenciaDtoSinFactores,
  type PuestoDto,
  type PuestoDtoCreate,
} from "@/lib/backend-api";

type CompetenciaPuestoEditada = {
  idCompetencia: string;
  ponderacion: string;
};

type FormularioPuesto = {
  nombre: string;
  descripcion: string;
  empresa: string;
  caracteristicas: CompetenciaPuestoEditada[];
};

function ResultadosPuestos() {
  const searchParams = useSearchParams();
  const codigo = searchParams.get("codigo")?.trim() ?? "";
  const empresa = searchParams.get("empresa")?.trim() ?? "";
  const nombre = searchParams.get("puesto")?.trim() ?? "";
  const [resultado, setResultado] = useState<{
    query: string;
    puestos: PuestoDto[];
    error: string;
    ready: boolean;
  }>({ query: "", puestos: [], error: "", ready: false });
  const [seleccionado, setSeleccionado] = useState<number[]>([]);
  const [puestoEnEdicion, setPuestoEnEdicion] = useState<PuestoDto | null>(null);
  const [formularioEdicion, setFormularioEdicion] = useState<FormularioPuesto>({
    nombre: "",
    descripcion: "",
    empresa: "",
    caracteristicas: [],
  });
  const [competencias, setCompetencias] = useState<CompetenciaDtoSinFactores[]>([]);
  const [cargandoCompetencias, setCargandoCompetencias] = useState(false);
  const [guardando, setGuardando] = useState(false);
  const [errorMutacion, setErrorMutacion] = useState("");
  const [mensaje, setMensaje] = useState("");

  useEffect(() => {
    const parametros = new URLSearchParams();
    if (codigo) parametros.set("codigo", codigo);
    if (empresa) parametros.set("empresa", empresa);
    if (nombre) parametros.set("puesto", nombre);
    const query = parametros.toString();
    if (!query) return;

    let activo = true;
    backendRequest<PuestoDto[]>(
      `/puestos/buscar?${query}`
    )
      .then((resultados) => {
        if (activo) {
          setResultado({ query, puestos: resultados, error: "", ready: true });
          setSeleccionado([]);
        }
      })
      .catch((solicitudError: unknown) => {
        if (activo) {
          setResultado({
            query,
            puestos: [],
            error: getErrorMessage(solicitudError),
            ready: true,
          });
        }
      });

    return () => {
      activo = false;
    };
  }, [codigo, empresa, nombre]);

  const parametrosActuales = new URLSearchParams();
  if (codigo) parametrosActuales.set("codigo", codigo);
  if (empresa) parametrosActuales.set("empresa", empresa);
  if (nombre) parametrosActuales.set("puesto", nombre);
  const query = parametrosActuales.toString();
  const cargando = Boolean(query) && (!resultado.ready || resultado.query !== query);
  const resultadoActual = Boolean(query) && resultado.query === query && resultado.ready;
  const puestos = resultadoActual ? resultado.puestos : [];
  const error = resultadoActual ? resultado.error : "";
  const puestosTabla = puestos.map((puesto) => [
    puesto.codigo,
    puesto.nombre,
    puesto.empresa,
  ]);

  const abrirEdicion = async () => {
    const puesto = seleccionado.length > 0 ? puestos[seleccionado[0]] : undefined;
    if (!puesto) return;
    setErrorMutacion("");
    setMensaje("");
    setCargandoCompetencias(true);
    try {
      const catalogo = await backendRequest<CompetenciaDtoSinFactores[]>(
        "/gestionar-competencias/lista"
      );
      setCompetencias(catalogo);
      setPuestoEnEdicion(puesto);
      setFormularioEdicion({
        nombre: puesto.nombre,
        descripcion: puesto.descripcion,
        empresa: puesto.empresa,
        caracteristicas: puesto.caracteristicas.map((caracteristica) => ({
          idCompetencia: String(caracteristica.idCompetencia),
          ponderacion: String(caracteristica.ponderacion),
        })),
      });
    } catch (errorSolicitud: unknown) {
      setErrorMutacion(getErrorMessage(errorSolicitud));
    } finally {
      setCargandoCompetencias(false);
    }
  };

  const guardarModificacion = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!puestoEnEdicion) return;

    if (!formularioEdicion.nombre.trim() || formularioEdicion.nombre.length > 50) {
      setErrorMutacion("El nombre es obligatorio y no puede superar 50 caracteres.");
      return;
    }
    if (
      formularioEdicion.descripcion.trim().length < 30 ||
      formularioEdicion.descripcion.length > 300
    ) {
      setErrorMutacion("La descripción debe tener entre 30 y 300 caracteres.");
      return;
    }
    if (!formularioEdicion.empresa.trim() || formularioEdicion.empresa.length > 50) {
      setErrorMutacion("La empresa es obligatoria y no puede superar 50 caracteres.");
      return;
    }
    if (formularioEdicion.caracteristicas.length < 2) {
      setErrorMutacion("El puesto debe conservar al menos dos competencias.");
      return;
    }

    const caracteristicas: Record<number, number> = {};
    for (const item of formularioEdicion.caracteristicas) {
      const idCompetencia = item.idCompetencia;
      const valor = item.ponderacion;
      const ponderacion = Number(valor);
      if (
        !isPositiveLongIdentifier(idCompetencia) ||
        valor.trim() === "" ||
        !Number.isInteger(ponderacion) ||
        ponderacion < 0 ||
        ponderacion > 10
      ) {
        setErrorMutacion("Cada competencia debe tener una ponderación entera entre 0 y 10.");
        return;
      }
      caracteristicas[Number(idCompetencia)] = ponderacion;
    }

    if (Object.keys(caracteristicas).length !== formularioEdicion.caracteristicas.length) {
      setErrorMutacion("Cada competencia debe aparecer una sola vez.");
      return;
    }

    const body: PuestoDtoCreate = {
      codigo: puestoEnEdicion.codigo,
      nombre: formularioEdicion.nombre.trim(),
      descripcion: formularioEdicion.descripcion.trim(),
      empresa: formularioEdicion.empresa.trim(),
      caracteristicas,
    };

    setGuardando(true);
    setErrorMutacion("");
    try {
      const actualizado = await backendRequest<PuestoDto>(
        `/puestos/update/${encodeURIComponent(String(puestoEnEdicion.idPuesto))}`,
        {
          method: "PATCH",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(body),
        }
      );
      setResultado((actual) => ({
        ...actual,
        puestos: actual.puestos.map((item) =>
          item.idPuesto === actualizado.idPuesto ? actualizado : item
        ),
      }));
      setSeleccionado([]);
      setPuestoEnEdicion(null);
      setMensaje(`Puesto actualizado: ${actualizado.codigo} — ${actualizado.nombre}.`);
    } catch (errorSolicitud: unknown) {
      setErrorMutacion(getErrorMessage(errorSolicitud));
    } finally {
      setGuardando(false);
    }
  };

  const agregarCompetencia = () => {
    setFormularioEdicion((actual) => ({
      ...actual,
      caracteristicas: [
        ...actual.caracteristicas,
        { idCompetencia: "", ponderacion: "" },
      ],
    }));
  };

  const quitarCompetencia = (indice: number) => {
    setFormularioEdicion((actual) => ({
      ...actual,
      caracteristicas:
        actual.caracteristicas.length > 2
          ? actual.caracteristicas.filter((_, index) => index !== indice)
          : actual.caracteristicas,
    }));
  };

  const eliminar = async () => {
    const puesto = seleccionado.length > 0 ? puestos[seleccionado[0]] : undefined;
    if (!puesto) return;

    const idConsultorLimpio = DEFAULT_DELETE_CONSULTOR_ID;
    const idPuesto = String(puesto.idPuesto);
    if (
      !isPositiveLongIdentifier(idPuesto) ||
      !isPositiveLongIdentifier(idConsultorLimpio)
    ) {
      setErrorMutacion("Los IDs de puesto y consultor deben ser enteros positivos válidos.");
      return;
    }

    setGuardando(true);
    setErrorMutacion("");
    setMensaje("");
    try {
      const respuesta = await backendRequest<string>(
        `/puestos/delete/${encodeURIComponent(idPuesto)}/responsable/${encodeURIComponent(idConsultorLimpio)}`,
        { method: "DELETE" }
      );
      setResultado((actual) => ({
        ...actual,
        puestos: actual.puestos.filter((item) => item.idPuesto !== puesto.idPuesto),
      }));
      setSeleccionado([]);
      setMensaje(respuesta);
    } catch (errorSolicitud: unknown) {
      setErrorMutacion(getErrorMessage(errorSolicitud));
    } finally {
      setGuardando(false);
    }
  };

  return (
    <main style={{ minHeight: "100vh", padding: "4rem 1rem" }}>
      <section
        style={{
          maxWidth: "1000px",
          margin: "0 auto",
          border: "2px solid #1E2A1C",
          borderRadius: "8px",
          background: "#F5F5F5",
          padding: "1rem",
        }}
      >
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Puestos encontrados</h2>
        {query && (
          <Tabla
            columnas={["Código", "Nombre del puesto", "Empresa"]}
            datos={puestosTabla}
            onRowClick={(index) => setSeleccionado([index])}
            seleccionados={seleccionado}
          />
        )}
        {cargando && <p role="status">Cargando puestos...</p>}
        {error && (
          <FeedbackMessage variant="error">
            No se pudieron cargar los puestos: {error}
          </FeedbackMessage>
        )}
        {!cargando && !error && !query && (
          <FeedbackMessage variant="warning">
            Ingresá al menos un filtro en la pantalla anterior para buscar puestos.
          </FeedbackMessage>
        )}
        {!cargando && !error && query && puestos.length === 0 && (
          <FeedbackMessage variant="warning">
            No hay puestos que coincidan con la búsqueda.
          </FeedbackMessage>
        )}
        {!cargando && !error && puestos.length > 0 && (
          <div style={{ display: "flex", justifyContent: "center", gap: "1rem", marginTop: "1rem" }}>
            <Boton
              texto="Modificar"
              type="button"
              disabled={seleccionado.length === 0 || guardando || cargandoCompetencias}
              onClick={abrirEdicion}
            />
            <Boton
              texto="Eliminar"
              type="button"
              disabled={seleccionado.length === 0 || guardando}
              onClick={() => void eliminar()}
            />
          </div>
        )}
        {errorMutacion && (
          <FeedbackMessage variant="error">
            No se pudo completar la operación: {errorMutacion}
          </FeedbackMessage>
        )}
        {mensaje && <FeedbackMessage variant="success">{mensaje}</FeedbackMessage>}
      </section>

      {puestoEnEdicion && (
        <div
          role="dialog"
          aria-modal="true"
          aria-labelledby="titulo-edicion-puesto"
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
              maxWidth: "760px",
              maxHeight: "90vh",
              overflowY: "auto",
              background: "#F5F5F5",
              borderRadius: "8px",
              padding: "1.5rem",
            }}
          >
            <h2 id="titulo-edicion-puesto" style={{ textAlign: "center" }}>
              Modificar puesto
            </h2>
            <p>
              Código: {puestoEnEdicion.codigo}. El código no se puede modificar.
            </p>
            <form onSubmit={guardarModificacion}>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Nombre
                <input
                  value={formularioEdicion.nombre}
                  onChange={(event) =>
                    setFormularioEdicion((actual) => ({
                      ...actual,
                      nombre: event.target.value,
                    }))
                  }
                  maxLength={50}
                  required
                  style={{ width: "100%", padding: "0.6rem" }}
                />
              </label>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Descripción
                <textarea
                  value={formularioEdicion.descripcion}
                  onChange={(event) =>
                    setFormularioEdicion((actual) => ({
                      ...actual,
                      descripcion: event.target.value,
                    }))
                  }
                  minLength={30}
                  maxLength={300}
                  required
                  rows={4}
                  style={{ width: "100%", padding: "0.6rem" }}
                />
              </label>
              <label style={{ display: "block", margin: "0.75rem 0" }}>
                Empresa
                <input
                  value={formularioEdicion.empresa}
                  onChange={(event) =>
                    setFormularioEdicion((actual) => ({
                      ...actual,
                      empresa: event.target.value,
                    }))
                  }
                  maxLength={50}
                  required
                  style={{ width: "100%", padding: "0.6rem" }}
                />
              </label>
              <h3>Competencias y ponderaciones</h3>
              {formularioEdicion.caracteristicas.map((caracteristica, index) => {
                const seleccionadasEnOtrasFilas = new Set(
                  formularioEdicion.caracteristicas
                    .filter((_, otroIndice) => otroIndice !== index)
                    .map((item) => item.idCompetencia)
                    .filter(Boolean)
                );

                return (
                  <div key={index} style={{ display: "flex", gap: "0.75rem", alignItems: "end", margin: "0.75rem 0" }}>
                    <label style={{ flex: 2 }}>
                      Competencia
                      <select
                        value={caracteristica.idCompetencia}
                        onChange={(event) =>
                          setFormularioEdicion((actual) => ({
                            ...actual,
                            caracteristicas: actual.caracteristicas.map((item, otroIndice) =>
                              otroIndice === index
                                ? { ...item, idCompetencia: event.target.value }
                                : item
                            ),
                          }))
                        }
                        required
                        style={{ display: "block", width: "100%", padding: "0.6rem" }}
                      >
                        <option value="">Seleccioná una competencia</option>
                        {competencias.map((competencia) => (
                          <option
                            key={competencia.id}
                            value={String(competencia.id)}
                            disabled={seleccionadasEnOtrasFilas.has(String(competencia.id))}
                          >
                            {competencia.nombre}
                          </option>
                        ))}
                      </select>
                    </label>
                    <label style={{ flex: 1 }}>
                      Ponderación
                      <input
                        type="number"
                        min="0"
                        max="10"
                        step="1"
                        value={caracteristica.ponderacion}
                        onChange={(event) =>
                          setFormularioEdicion((actual) => ({
                            ...actual,
                            caracteristicas: actual.caracteristicas.map((item, otroIndice) =>
                              otroIndice === index
                                ? { ...item, ponderacion: event.target.value }
                                : item
                            ),
                          }))
                        }
                        required
                        style={{ display: "block", width: "100%", padding: "0.6rem" }}
                      />
                    </label>
                    <button
                      type="button"
                      onClick={() => quitarCompetencia(index)}
                      disabled={formularioEdicion.caracteristicas.length <= 2}
                    >
                      Quitar
                    </button>
                  </div>
                );
              })}
              <button
                type="button"
                onClick={agregarCompetencia}
                disabled={
                  formularioEdicion.caracteristicas.length >= competencias.length
                }
              >
                Agregar competencia
              </button>
              {errorMutacion && (
                <FeedbackMessage variant="error">{errorMutacion}</FeedbackMessage>
              )}
              <div style={{ display: "flex", justifyContent: "center", gap: "0.75rem", marginTop: "1rem" }}>
                <Boton texto={guardando ? "Guardando..." : "Guardar cambios"} type="submit" disabled={guardando} />
                <Boton texto="Cancelar" type="button" onClick={() => setPuestoEnEdicion(null)} disabled={guardando} />
              </div>
            </form>
          </section>
        </div>
      )}
    </main>
  );
}

export default function GestionarPuestosResultados() {
  return (
    <Suspense fallback={<p role="status">Cargando búsqueda...</p>}>
      <ResultadosPuestos />
    </Suspense>
  );
}
