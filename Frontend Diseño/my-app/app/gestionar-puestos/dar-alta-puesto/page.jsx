"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import Boton from "../../../componentes/boton";
import InputMayus from "../../../componentes/inputMayus";
import { backendRequest, getErrorMessage } from "@/lib/backend-api";

const formInicial = {
  codigo: "",
  nombre: "",
  descripcion: "",
  empresa: "",
};

const filasIniciales = () => [
  { id: 1, competenciaId: "", ponderacion: "" },
  { id: 2, competenciaId: "", ponderacion: "" },
];

export default function AltaPuesto() {
  const [formData, setFormData] = useState(formInicial);
  const [competencias, setCompetencias] = useState([]);
  const [filas, setFilas] = useState(filasIniciales);
  const [siguienteFilaId, setSiguienteFilaId] = useState(3);
  const [cargandoCatalogo, setCargandoCatalogo] = useState(true);
  const [errorCatalogo, setErrorCatalogo] = useState("");
  const [guardando, setGuardando] = useState(false);
  const [error, setError] = useState("");
  const [puestoCreado, setPuestoCreado] = useState(null);

  useEffect(() => {
    let activo = true;
    backendRequest("/gestionar-competencias/lista")
      .then((resultado) => {
        if (activo) setCompetencias(resultado);
      })
      .catch((errorSolicitud) => {
        if (activo) setErrorCatalogo(getErrorMessage(errorSolicitud));
      })
      .finally(() => {
        if (activo) setCargandoCatalogo(false);
      });

    return () => {
      activo = false;
    };
  }, []);

  const handleChange = (campo) => (event) => {
    setFormData((actual) => ({ ...actual, [campo]: event.target.value }));
  };

  const actualizarFila = (id, campo, valor) => {
    setFilas((actuales) =>
      actuales.map((fila) =>
        fila.id === id ? { ...fila, [campo]: valor } : fila
      )
    );
  };

  const agregarFila = () => {
    setFilas((actuales) => [
      ...actuales,
      { id: siguienteFilaId, competenciaId: "", ponderacion: "" },
    ]);
    setSiguienteFilaId((actual) => actual + 1);
  };

  const quitarFila = (id) => {
    setFilas((actuales) =>
      actuales.length > 2 ? actuales.filter((fila) => fila.id !== id) : actuales
    );
  };

  const crearPuesto = async (event) => {
    event.preventDefault();
    setError("");

    if (formData.codigo.trim().length === 0 || formData.codigo.length > 30) {
      setError("El código es obligatorio y no puede superar 30 caracteres.");
      return;
    }
    if (formData.nombre.trim().length === 0 || formData.nombre.length > 50) {
      setError("El nombre es obligatorio y no puede superar 50 caracteres.");
      return;
    }
    if (formData.descripcion.trim().length < 30 || formData.descripcion.length > 300) {
      setError("La descripción debe tener entre 30 y 300 caracteres.");
      return;
    }
    if (formData.empresa.trim().length === 0 || formData.empresa.length > 50) {
      setError("La empresa es obligatoria y no puede superar 50 caracteres.");
      return;
    }
    if (filas.length < 2 || filas.some((fila) => !fila.competenciaId)) {
      setError("Elegí una competencia en cada fila; se requieren al menos dos.");
      return;
    }

    const idsCompetencia = filas.map((fila) => fila.competenciaId);
    if (new Set(idsCompetencia).size !== idsCompetencia.length) {
      setError("Cada competencia solo puede seleccionarse una vez.");
      return;
    }

    const caracteristicas = {};
    for (const fila of filas) {
      const ponderacion = Number(fila.ponderacion);
      if (
        fila.ponderacion === "" ||
        !Number.isInteger(ponderacion) ||
        ponderacion < 0 ||
        ponderacion > 10
      ) {
        setError("Cada ponderación debe ser un número entero entre 0 y 10.");
        return;
      }
      caracteristicas[Number(fila.competenciaId)] = ponderacion;
    }

    setGuardando(true);
    try {
      const resultado = await backendRequest("/puestos/new", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          codigo: formData.codigo.trim(),
          nombre: formData.nombre.trim(),
          descripcion: formData.descripcion.trim(),
          empresa: formData.empresa.trim(),
          caracteristicas,
        }),
      });
      setPuestoCreado(resultado);
    } catch (errorSolicitud) {
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setGuardando(false);
    }
  };

  const reiniciar = () => {
    setFormData(formInicial);
    setFilas(filasIniciales());
    setSiguienteFilaId(3);
    setPuestoCreado(null);
    setError("");
  };

  const competenciasDisponibles = competencias.filter(
    (competencia) =>
      !filas.some((fila) => fila.competenciaId === String(competencia.id))
  );

  return (
    <main style={{ minHeight: "100vh", padding: "4rem 1rem" }}>
      <section
        style={{
          maxWidth: "900px",
          margin: "0 auto",
          border: "2px solid #1E2A1C",
          borderRadius: "8px",
          background: "#F5F5F5",
          padding: "1.5rem",
        }}
      >
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Dar de alta puesto</h2>
        {cargandoCatalogo && <p role="status">Cargando competencias...</p>}
        {!cargandoCatalogo && competencias.length < 2 && !errorCatalogo && (
          <p>El backend debe devolver al menos dos competencias para crear un puesto.</p>
        )}

        <form onSubmit={crearPuesto}>
          <div style={{ display: "flex", flexWrap: "wrap", justifyContent: "center", gap: "1rem" }}>
            <InputMayus
              label="Código:"
              placeholder="Código"
              value={formData.codigo}
              onChange={handleChange("codigo")}
            />
            <InputMayus
              label="Nombre del puesto:"
              placeholder="Nombre"
              value={formData.nombre}
              onChange={handleChange("nombre")}
            />
            <InputMayus
              label="Descripción:"
              placeholder="Descripción (30 a 300 caracteres)"
              value={formData.descripcion}
              onChange={handleChange("descripcion")}
            />
            <InputMayus
              label="Empresa:"
              placeholder="Empresa"
              value={formData.empresa}
              onChange={handleChange("empresa")}
            />
          </div>

          <fieldset style={{ margin: "1.5rem 0", border: "1px solid #1E2A1C", padding: "1rem" }}>
            <legend>Competencias requeridas y ponderaciones</legend>
            <p style={{ marginBottom: "1rem" }}>
              Elegí al menos dos competencias y asigná a cada una una ponderación de 0 a 10.
            </p>
            <div style={{ overflowX: "auto" }}>
              <table style={{ width: "100%", borderCollapse: "collapse", minWidth: "520px" }}>
                <thead>
                  <tr style={{ background: "#E0E4D3" }}>
                    <th scope="col" style={{ border: "1px solid #1E2A1C", padding: "0.65rem", textAlign: "left" }}>
                      Competencia
                    </th>
                    <th scope="col" style={{ border: "1px solid #1E2A1C", padding: "0.65rem", textAlign: "left" }}>
                      Ponderación
                    </th>
                    <th scope="col" style={{ border: "1px solid #1E2A1C", padding: "0.65rem" }}>
                      Acción
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {filas.map((fila, index) => {
                    const seleccionadasEnOtrasFilas = new Set(
                      filas
                        .filter(
                          (otraFila) =>
                            otraFila.id !== fila.id && otraFila.competenciaId
                        )
                        .map((otraFila) => otraFila.competenciaId)
                    );

                    return (
                      <tr key={fila.id}>
                        <td style={{ border: "1px solid #1E2A1C", padding: "0.5rem" }}>
                          <select
                            aria-label={`Competencia de la fila ${index + 1}`}
                            value={fila.competenciaId}
                            onChange={(event) =>
                              actualizarFila(fila.id, "competenciaId", event.target.value)
                            }
                            required
                            disabled={cargandoCatalogo || Boolean(errorCatalogo)}
                            style={{
                              width: "100%",
                              padding: "0.55rem",
                              border: "1px solid #1E2A1C",
                              borderRadius: "4px",
                              background: "white",
                            }}
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
                        </td>
                        <td style={{ border: "1px solid #1E2A1C", padding: "0.5rem" }}>
                          <input
                            type="number"
                            aria-label={`Ponderación de la fila ${index + 1}`}
                            min="0"
                            max="10"
                            step="1"
                            value={fila.ponderacion}
                            onChange={(event) =>
                              actualizarFila(fila.id, "ponderacion", event.target.value)
                            }
                            required
                            style={{
                              width: "100%",
                              padding: "0.55rem",
                              border: "1px solid #1E2A1C",
                              borderRadius: "4px",
                            }}
                          />
                        </td>
                        <td style={{ border: "1px solid #1E2A1C", padding: "0.5rem", textAlign: "center" }}>
                          <button
                            type="button"
                            onClick={() => quitarFila(fila.id)}
                            disabled={filas.length <= 2}
                            aria-label={`Quitar fila ${index + 1}`}
                            style={{
                              padding: "0.5rem",
                              border: "1px solid #1E2A1C",
                              borderRadius: "4px",
                              background: filas.length <= 2 ? "#D5D8D0" : "#F5F5F5",
                              cursor: filas.length <= 2 ? "not-allowed" : "pointer",
                            }}
                          >
                            Quitar
                          </button>
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
            <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "0.75rem" }}>
              <button
                type="button"
                onClick={agregarFila}
                disabled={cargandoCatalogo || competenciasDisponibles.length === 0}
                style={{
                  padding: "0.6rem 0.9rem",
                  border: "1px solid #1E2A1C",
                  borderRadius: "4px",
                  background: competenciasDisponibles.length === 0 ? "#D5D8D0" : "#E0E4D3",
                  cursor: competenciasDisponibles.length === 0 ? "not-allowed" : "pointer",
                }}
              >
                Agregar competencia
              </button>
            </div>
          </fieldset>

          {errorCatalogo && (
            <p role="alert">No se pudieron cargar las competencias: {errorCatalogo}</p>
          )}
          {error && <p role="alert">No se pudo crear el puesto: {error}</p>}
          <div style={{ display: "flex", justifyContent: "center", gap: "1rem" }}>
            <Boton
              type="submit"
              texto={guardando ? "Guardando..." : "Aceptar"}
              disabled={
                guardando ||
                cargandoCatalogo ||
                Boolean(errorCatalogo) ||
                competencias.length < 2
              }
            />
            <Link href="/gestionar-puestos">
              <Boton texto="Cancelar" type="button" />
            </Link>
          </div>
        </form>
      </section>

      {puestoCreado && (
        <div
          role="dialog"
          aria-modal="true"
          style={{
            position: "fixed",
            inset: 0,
            background: "rgba(0,0,0,0.5)",
            display: "grid",
            placeItems: "center",
            zIndex: 1000,
          }}
        >
          <div style={{ background: "#f1f1f1", padding: "2rem", borderRadius: "4px" }}>
            <p>
              Puesto creado desde el backend: {puestoCreado.codigo} — {puestoCreado.nombre}
            </p>
            <div style={{ display: "flex", justifyContent: "center", gap: "1rem" }}>
              <Link href="/gestionar-puestos">
                <Boton texto="Volver a puestos" type="button" />
              </Link>
              <Boton texto="Crear otro" onClick={reiniciar} />
            </div>
          </div>
        </div>
      )}
    </main>
  );
}
