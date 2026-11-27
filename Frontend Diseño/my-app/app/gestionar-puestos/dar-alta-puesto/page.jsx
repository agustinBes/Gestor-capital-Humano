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

export default function AltaPuesto() {
  const [formData, setFormData] = useState(formInicial);
  const [competencias, setCompetencias] = useState([]);
  const [ponderaciones, setPonderaciones] = useState({});
  const [cargandoCatalogo, setCargandoCatalogo] = useState(true);
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
        if (activo) setError(getErrorMessage(errorSolicitud));
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

  const seleccionarCompetencia = (id, seleccionada) => {
    setPonderaciones((actuales) => {
      const resultado = { ...actuales };
      if (seleccionada) {
        resultado[id] = "";
      } else {
        delete resultado[id];
      }
      return resultado;
    });
  };

  const crearPuesto = async (event) => {
    event.preventDefault();
    setError("");

    const seleccionadas = Object.entries(ponderaciones);
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
    if (seleccionadas.length < 2) {
      setError("Seleccioná al menos dos competencias recuperadas del backend.");
      return;
    }

    const caracteristicas = {};
    for (const [id, valor] of seleccionadas) {
      const ponderacion = Number(valor);
      if (!Number.isInteger(ponderacion) || ponderacion < 0 || ponderacion > 10) {
        setError("Cada ponderación debe ser un número entero entre 0 y 10.");
        return;
      }
      caracteristicas[id] = ponderacion;
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
    setPonderaciones({});
    setPuestoCreado(null);
    setError("");
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
          padding: "1.5rem",
        }}
      >
        <h2 style={{ textAlign: "center", fontSize: "2rem" }}>Dar de alta puesto</h2>
        {cargandoCatalogo && <p role="status">Cargando competencias...</p>}
        {!cargandoCatalogo && competencias.length === 0 && !error && (
          <p>No hay competencias disponibles en el backend.</p>
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
            <legend>Competencias y ponderaciones del DTO del puesto</legend>
            {competencias.map((competencia) => {
              const seleccionada = Object.prototype.hasOwnProperty.call(
                ponderaciones,
                competencia.id
              );
              return (
                <label
                  key={competencia.id}
                  style={{ display: "flex", alignItems: "center", gap: "0.5rem", margin: "0.5rem 0" }}
                >
                  <input
                    type="checkbox"
                    checked={seleccionada}
                    onChange={(event) =>
                      seleccionarCompetencia(competencia.id, event.target.checked)
                    }
                  />
                  {competencia.nombre}
                  {seleccionada && (
                    <input
                      type="number"
                      min="0"
                      max="10"
                      step="1"
                      aria-label={`Ponderación de ${competencia.nombre}`}
                      value={ponderaciones[competencia.id]}
                      onChange={(event) =>
                        setPonderaciones((actuales) => ({
                          ...actuales,
                          [competencia.id]: event.target.value,
                        }))
                      }
                    />
                  )}
                </label>
              );
            })}
          </fieldset>

          {error && <p role="alert">No se pudo crear el puesto: {error}</p>}
          <div style={{ display: "flex", justifyContent: "center", gap: "1rem" }}>
            <Boton
              type="submit"
              texto={guardando ? "Guardando..." : "Aceptar"}
              disabled={guardando || cargandoCatalogo || competencias.length === 0}
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
