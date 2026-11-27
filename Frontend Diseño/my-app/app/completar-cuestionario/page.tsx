"use client";

import { useState, type FormEvent } from "react";
import { useRouter } from "next/navigation";
import Boton from "../../componentes/boton";
import {
  AUTHENTICATED_QUESTIONNAIRE_SESSION_KEY,
  backendRequest,
  getErrorMessage,
  type CuestionarioDto,
} from "@/lib/backend-api";

export default function AutenticarCandidato() {
  const router = useRouter();
  const [tipoDocumento, setTipoDocumento] = useState("DNI");
  const [documento, setDocumento] = useState("");
  const [clave, setClave] = useState("");
  const [autenticando, setAutenticando] = useState(false);
  const [error, setError] = useState("");

  const autenticar = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setAutenticando(true);
    setError("");

    try {
      const cuestionario = await backendRequest<CuestionarioDto>(
        "/gestionar-candidatos/candidato/auth",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            tipoDocumento,
            documento: documento.trim(),
            clave,
          }),
        }
      );

      if (!Number.isFinite(cuestionario.idCuestionario)) {
        throw new Error("El backend no devolvió un cuestionario válido.");
      }

      sessionStorage.setItem(
        AUTHENTICATED_QUESTIONNAIRE_SESSION_KEY,
        JSON.stringify(cuestionario)
      );
      router.push(
        `/completar-cuestionario/completar-cuestionario-pagina2?idCuestionario=${cuestionario.idCuestionario}`
      );
    } catch (errorSolicitud: unknown) {
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setAutenticando(false);
    }
  };

  return (
    <main
      style={{
        minHeight: "100vh",
        padding: "4rem 1rem",
        background: "#BCC9A7",
        color: "#1D2B1B",
      }}
    >
      <section
        style={{
          maxWidth: "520px",
          margin: "0 auto",
          border: "2px solid #1E2A1C",
          borderRadius: "8px",
          background: "#F5F5F5",
          padding: "2rem",
        }}
      >
        <h1 style={{ textAlign: "center", fontSize: "2rem", marginBottom: "0.5rem" }}>
          Acceso al cuestionario
        </h1>
        <p style={{ textAlign: "center", marginBottom: "1.5rem" }}>
          Ingresá tus datos para recuperar tu cuestionario.
        </p>

        <form onSubmit={autenticar}>
          <label htmlFor="tipoDocumento" style={{ display: "block", marginBottom: "0.35rem" }}>
            Tipo de documento
          </label>
          <select
            id="tipoDocumento"
            name="tipoDocumento"
            value={tipoDocumento}
            onChange={(event) => setTipoDocumento(event.target.value)}
            required
            style={{
              width: "100%",
              padding: "0.65rem",
              marginBottom: "1rem",
              border: "1px solid #667260",
              borderRadius: "4px",
              background: "white",
            }}
          >
            <option value="DNI">DNI</option>
            <option value="LE">LE</option>
            <option value="LC">LC</option>
            <option value="PP">PP</option>
          </select>

          <label htmlFor="documento" style={{ display: "block", marginBottom: "0.35rem" }}>
            Número de documento
          </label>
          <input
            id="documento"
            name="documento"
            type="text"
            value={documento}
            onChange={(event) => setDocumento(event.target.value)}
            autoComplete="username"
            minLength={6}
            maxLength={15}
            pattern="[0-9A-Za-z]{6,15}"
            title="Ingresá entre 6 y 15 caracteres alfanuméricos, sin puntos ni guiones."
            required
            style={{
              width: "100%",
              padding: "0.65rem",
              marginBottom: "1rem",
              border: "1px solid #667260",
              borderRadius: "4px",
            }}
          />

          <label htmlFor="clave" style={{ display: "block", marginBottom: "0.35rem" }}>
            Clave de ingreso
          </label>
          <input
            id="clave"
            name="clave"
            type="password"
            value={clave}
            onChange={(event) => setClave(event.target.value)}
            autoComplete="current-password"
            minLength={8}
            maxLength={8}
            required
            style={{
              width: "100%",
              padding: "0.65rem",
              marginBottom: "1.25rem",
              border: "1px solid #667260",
              borderRadius: "4px",
            }}
          />

          {error && (
            <p role="alert" style={{ color: "#9B1C1C", marginBottom: "1rem" }}>
              No se pudo autenticar: {error}
            </p>
          )}

          <div style={{ display: "flex", justifyContent: "center" }}>
            <Boton
              texto={autenticando ? "Verificando..." : "Ingresar"}
              type="submit"
              disabled={autenticando}
            />
          </div>
        </form>
      </section>
    </main>
  );
}
