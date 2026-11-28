"use client";

import { useState } from "react";
import Link from "next/link";
import Boton from "../../componentes/boton";
import FeedbackMessage from "@/componentes/feedback-message";
import Tabla from "../../componentes/tabla";
import { backendRequest, getErrorMessage } from "@/lib/backend-api";

type AuditoriaDto = {
  id: number;
  id_entidad: number;
  registro: string | null;
  tipo: string;
  fecha: string;
  consultor: string | null;
};

export default function GestionarAuditorias() {
  const [auditorias, setAuditorias] = useState<AuditoriaDto[]>([]);
  const [consultaRealizada, setConsultaRealizada] = useState(false);
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState("");

  const listarAuditorias = async () => {
    setConsultaRealizada(true);
    setCargando(true);
    setError("");

    try {
      const resultado = await backendRequest<AuditoriaDto[]>("/auditorias/lista");
      setAuditorias(resultado);
    } catch (errorSolicitud: unknown) {
      setAuditorias([]);
      setError(getErrorMessage(errorSolicitud));
    } finally {
      setCargando(false);
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
        <h1 style={{ textAlign: "center", fontSize: "2rem" }}>Auditorías</h1>
        <p style={{ textAlign: "center" }}>
          Consultá los registros de auditoría generados por el sistema.
        </p>

        <div style={{ display: "flex", justifyContent: "center", gap: "0.75rem", flexWrap: "wrap" }}>
          <Boton
            texto={cargando ? "Cargando..." : "Ver todas las auditorías"}
            onClick={() => void listarAuditorias()}
            disabled={cargando}
          />
          <Link href="/">
            <Boton texto="Inicio" type="button" />
          </Link>
        </div>

        {cargando && <p role="status">Consultando auditorías...</p>}
        {error && <FeedbackMessage variant="error">{error}</FeedbackMessage>}
        {!consultaRealizada && (
          <FeedbackMessage variant="warning">
            Seleccioná “Ver todas las auditorías” para consultar los registros.
          </FeedbackMessage>
        )}
        {consultaRealizada && !cargando && !error && auditorias.length === 0 && (
          <FeedbackMessage variant="warning">
            No hay auditorías registradas.
          </FeedbackMessage>
        )}
        {consultaRealizada && auditorias.length > 0 && (
          <div style={{ overflowX: "auto" }}>
            <Tabla
              columnas={["ID auditoría", "ID entidad", "Tipo", "Registro", "Fecha", "Consultor"]}
              datos={auditorias.map((auditoria) => [
                auditoria.id,
                auditoria.id_entidad,
                auditoria.tipo,
                auditoria.registro ?? "—",
                auditoria.fecha,
                auditoria.consultor ?? "—",
              ])}
            />
          </div>
        )}
      </section>
    </main>
  );
}
