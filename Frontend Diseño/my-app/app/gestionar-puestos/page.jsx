"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Boton from "../../componentes/boton";
import Link from "next/dist/client/link";
import Input from "../../componentes/input";
import Input2 from "../../componentes/input2";

export default function CompletarCuestionario() {
  const router = useRouter();

  const [codigo, setCodigo] = useState("");
  const [empresa, setEmpresa] = useState("");
  const [nombrePuesto, setNombrePuesto] = useState("");

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

  const handleSubmit = (e) => {
    e.preventDefault();

    const hayAlMenosUnDato =
      codigo.trim() !== "" ||
      empresa.trim() !== "" ||
      nombrePuesto.trim() !== "";

    if (!hayAlMenosUnDato) {
      alert("Por favor, ingrese al menos un dato en alguno de los campos para buscar.");
      return;
    }

    const params = new URLSearchParams();
    if (codigo.trim()) params.set("codigo", codigo.trim());
    if (empresa.trim()) params.set("empresa", empresa.trim());
    if (nombrePuesto.trim()) params.set("puesto", nombrePuesto.trim());

    const queryString = params.toString();
    router.push(queryString ? `/gestionar-puestos/dos/?${queryString}` : "/gestionar-puestos/dos/");
  };

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
          Gestionar Puestos <br />
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
        <div id="formulario">
          <form onSubmit={handleSubmit}>
            <Input2
              label="Codigo:   "
              placeholder="Codigo"
              value={codigo}
              onChange={(e) => setCodigo(e.target.value)}
            />
            <br />
            <Input2
              label="Empresa:   "
              placeholder="Empresa"
              value={empresa}
              onChange={(e) => setEmpresa(e.target.value)}
            />
            <br />
            <Input2
              label="Nombre del puesto:   "
              placeholder="Puesto"
              value={nombrePuesto}
              onChange={(e) => setNombrePuesto(e.target.value)}
            />
            <div
              style={{
                display: "flex",
                gap: "50px",
                textAlign: "center",
                margin: "1rem 0",
                justifyContent: "center",
              }}
            >
              <Boton texto="Buscar puesto" padding="10px 20px" type="submit" />
            </div>
          </form>
        </div>
        <Link href="/gestionar-puestos/dar-alta-puesto">
          <Boton texto="Nuevo puesto" padding="10px 20px" />
        </Link>
      </div>
    </div>
  );
}
