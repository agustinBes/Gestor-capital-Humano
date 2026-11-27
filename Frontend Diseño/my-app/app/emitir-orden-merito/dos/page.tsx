"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Boton from "../../../componentes/boton";
import Input from "../../../componentes/input";
import Tabla from "../../../componentes/tabla";

export default function CompletarCuestionario() {
  const router = useRouter();

  const [datos, setDatos] = useState([
    ["038", "Lucas", "Besigniano", "a", "a"],
    ["069", "Agustina", "Luna", "a", "a"],
    ["016", "Micalea", "Gaitan", "a", "a"],
    ["067", "Ivan", "Cabañas", "a", "a"],
  ]);

  const [seleccionado, setSeleccionado] = useState<number[]>([]);

  const seleccionDeFila = (index: number) => {
    setSeleccionado((prev) => (prev.includes(index) ? [] : [index]));
  };

  const manejarEmitirOrden = () => {
    if (seleccionado.length === 0) {
      alert("Debe seleccionar una fila antes de emitir el orden de mérito.");
      return;
    }

    const filaSeleccionada = datos[seleccionado[0]];
    console.log("Emitiendo orden de mérito para:", filaSeleccionada);

    router.push("/emitir-orden-merito/dos/tres");
  };

  const [paddingArriba, setPaddingArriba] = useState("12rem");
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
          Emitir Orden de Merito
        </h2>
        <hr
          style={{
            width: "100%",
            border: "none",
            borderTop: "2px solid #1E2A1C",
            margin: "0.5rem 0rem 1.5rem  0rem",
            alignSelf: "stretch",
          }}
        />
        Seleccionar la fila correspondiente al puesto del cual se quiere emitir el orden de merito:
        <div id="Tabla">
          <div style={{ display: "flex", gap: "50px", textAlign: "center", margin: "1rem 0" }}>
            <Tabla
              columnas={["Codigo", "Puesto", "Empresa", "Candidatos", "Evaluaciones Completas"]}
              datos={datos}
              onRowClick={seleccionDeFila}
              seleccionados={seleccionado}
            />
          </div>
        </div>
        <div style={{ display: "flex", gap: "100px", textAlign: "center", margin: "1rem 0" }}>
          <Boton
            type="button"
            texto="Emitir orden de merito"
            padding="10px 20px"
            onClick={manejarEmitirOrden}
          />
        </div>
      </div>
    </div>
  );
}
