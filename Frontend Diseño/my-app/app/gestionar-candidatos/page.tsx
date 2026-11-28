"use client";

import { useState, type FormEvent } from "react";
import Link from "next/link";
import Boton from "../../componentes/boton";
import FeedbackMessage from "@/componentes/feedback-message";
import Tabla from "../../componentes/tabla";
import {
  backendRequest,
  getErrorMessage,
  isPositiveLongIdentifier,
  type CandidatoDto,
} from "@/lib/backend-api";

const cabeceraCsv =
  "id,nombre,apellido,fecha,mail,tipo,documento,nacionalidad,genero,escolaridad";

export default function GestionarCandidatos() {
  const [candidatos, setCandidatos] = useState<CandidatoDto[]>([]);
  const [listaSolicitada, setListaSolicitada] = useState(false);
  const [cargandoLista, setCargandoLista] = useState(false);
  const [errorLista, setErrorLista] = useState("");
  const [idConsultor, setIdConsultor] = useState("");
  const [archivo, setArchivo] = useState<File | null>(null);
  const [importando, setImportando] = useState(false);
  const [errorImportacion, setErrorImportacion] = useState("");
  const [mensajeImportacion, setMensajeImportacion] = useState("");
  const [tipoMensaje, setTipoMensaje] = useState<"success" | "warning">("success");

  const listarCandidatos = async () => {
    setListaSolicitada(true);
    setCargandoLista(true);
    setErrorLista("");

    try {
      const resultado = await backendRequest<CandidatoDto[]>("/gestionar-candidatos/lista");
      setCandidatos(resultado);
    } catch (errorSolicitud: unknown) {
      setCandidatos([]);
      setErrorLista(getErrorMessage(errorSolicitud));
    } finally {
      setCargandoLista(false);
    }
  };

  const importarCandidatos = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorImportacion("");
    setMensajeImportacion("");

    const idConsultorLimpio = idConsultor.trim();
    if (!isPositiveLongIdentifier(idConsultorLimpio)) {
      setErrorImportacion("Ingresá un ID de consultor válido (entero positivo).");
      return;
    }
    if (!archivo) {
      setErrorImportacion("Seleccioná un archivo CSV para importar.");
      return;
    }
    if (!archivo.name.toLocaleLowerCase().endsWith(".csv")) {
      setErrorImportacion("El archivo debe tener extensión .csv.");
      return;
    }
    if (archivo.size === 0) {
      setErrorImportacion("El archivo CSV está vacío.");
      return;
    }

    const formulario = event.currentTarget;
    const datos = new FormData();
    datos.append("file", archivo);

    setImportando(true);
    try {
      const respuesta = await backendRequest<string>(
        `/gestionar-candidatos/import/${encodeURIComponent(idConsultorLimpio)}`,
        { method: "POST", body: datos }
      );
      setTipoMensaje(/algunos nros? de candidatos ya existen/i.test(respuesta) ? "warning" : "success");
      setMensajeImportacion(respuesta);
      setArchivo(null);
      formulario.reset();
    } catch (errorSolicitud: unknown) {
      setErrorImportacion(getErrorMessage(errorSolicitud));
    } finally {
      setImportando(false);
    }
  };

  return (
    <main style={{ minHeight: "100vh", padding: "4rem 1rem" }}>
      <section
        style={{
          maxWidth: "1200px",
          margin: "0 auto",
          border: "2px solid #1E2A1C",
          borderRadius: "8px",
          background: "#F5F5F5",
          padding: "1.5rem",
        }}
      >
        <h1 style={{ textAlign: "center", fontSize: "2rem" }}>Gestión de candidatos</h1>

        <section aria-labelledby="titulo-importar-candidatos" style={{ margin: "1.5rem 0" }}>
          <h2 id="titulo-importar-candidatos">Importar candidatos desde CSV</h2>
          <p>
            El archivo debe incluir esta cabecera y usar fechas con formato AAAA-MM-DD:
          </p>
          <code style={{ display: "block", overflowX: "auto", padding: "0.75rem", background: "#E0E4D3" }}>
            {cabeceraCsv}
          </code>
          <form onSubmit={importarCandidatos} style={{ marginTop: "1rem" }}>
            <ul className="form-field-list">
              <li>
                <label>
                  ID del consultor responsable
                  <input
                    value={idConsultor}
                    onChange={(event) => setIdConsultor(event.target.value)}
                    inputMode="numeric"
                    autoComplete="off"
                    required
                    disabled={importando}
                  />
                </label>
              </li>
              <li>
                <label>
                  Archivo CSV
                  <input
                    type="file"
                    accept=".csv,text/csv"
                    onChange={(event) => setArchivo(event.target.files?.[0] ?? null)}
                    required
                    disabled={importando}
                  />
                </label>
              </li>
            </ul>
            <div style={{ display: "flex", justifyContent: "center", marginTop: "1rem" }}>
              <Boton
                texto={importando ? "Importando..." : "Importar archivo"}
                type="submit"
                disabled={importando}
              />
            </div>
          </form>
          {errorImportacion && <FeedbackMessage variant="error">{errorImportacion}</FeedbackMessage>}
          {mensajeImportacion && (
            <FeedbackMessage variant={tipoMensaje}>{mensajeImportacion}</FeedbackMessage>
          )}
        </section>

        <hr style={{ border: 0, borderTop: "1px solid #667260", margin: "1.5rem 0" }} />

        <section aria-labelledby="titulo-listar-candidatos">
          <h2 id="titulo-listar-candidatos">Candidatos registrados</h2>
          <div style={{ display: "flex", justifyContent: "center", gap: "0.75rem", flexWrap: "wrap" }}>
            <Boton
              texto={cargandoLista ? "Cargando..." : "Listar todos los candidatos"}
              onClick={() => void listarCandidatos()}
              disabled={cargandoLista}
            />
            <Link href="/">
              <Boton texto="Inicio" type="button" />
            </Link>
          </div>

          {cargandoLista && <p role="status">Consultando candidatos...</p>}
          {errorLista && <FeedbackMessage variant="error">{errorLista}</FeedbackMessage>}
          {!listaSolicitada && (
            <FeedbackMessage variant="warning">
              Seleccioná “Listar todos los candidatos” para consultar los registros.
            </FeedbackMessage>
          )}
          {listaSolicitada && !cargandoLista && !errorLista && candidatos.length === 0 && (
            <FeedbackMessage variant="warning">No hay candidatos registrados.</FeedbackMessage>
          )}
          {listaSolicitada && candidatos.length > 0 && (
            <div style={{ overflowX: "auto" }}>
              <Tabla
                columnas={[
                  "ID",
                  "Nombre",
                  "Apellido",
                  "Fecha de nacimiento",
                  "Correo",
                  "Tipo de documento",
                  "Documento",
                  "Nacionalidad",
                  "Género",
                  "Escolaridad",
                ]}
                datos={candidatos.map((candidato) => [
                  candidato.id,
                  candidato.nombre,
                  candidato.apellido,
                  candidato.fecha,
                  candidato.mail,
                  candidato.tipo,
                  candidato.documento,
                  candidato.nacionalidad,
                  candidato.genero,
                  candidato.escolaridad,
                ])}
              />
            </div>
          )}
        </section>
      </section>
    </main>
  );
}
