"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Boton from "../../../componentes/boton";
import Link from "next/dist/client/link";
import Input from "../../../componentes/input";
import InputMayus from "../../../componentes/inputMayus";

// Simulación temporal de puestos existentes: reemplazar por consulta real a API/BD
const PUESTOS_EXISTENTES = [
  { codigo: "P001", nombrePuesto: "ANALISTA DE SISTEMAS" },
  { codigo: "P002", nombrePuesto: "DESARROLLADOR FRONTEND" },
];

const ETIQUETAS_CAMPOS = {
  codigo: "Codigo",
  nombrePuesto: "Nombre del puesto",
  descripcion: "Descripcion",
  empresa: "Empresa",
  caracteristicas: "Caracteristicas del puesto",
};

export default function CompletarCuestionario() {
  const router = useRouter();

  const [formData, setFormData] = useState({
    codigo: "",
    nombrePuesto: "",
    descripcion: "",
    empresa: "",
    caracteristicas: "",
  });

  const [errores, setErrores] = useState([]);
  const [mostrarModal, setMostrarModal] = useState(false);
  const [paddingArriba, setPaddingArriba] = useState("12rem");

  const handleChange = (campo) => (e) => {
    const valorEnMayusculas = e.target.value.toUpperCase();
    setFormData((prev) => ({
      ...prev,
      [campo]: valorEnMayusculas,
    }));
    if (errores.length > 0) {
      setErrores([]);
    }
  };

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

  const validarFormulario = () => {
    const mensajesError = [];
    let yaExiste = false;

    // 1. Verificar omisiones de campos obligatorios
    Object.entries(formData).forEach(([campo, valor]) => {
      if (!valor || valor.trim() === "") {
        mensajesError.push(`Falta completar el campo: ${ETIQUETAS_CAMPOS[campo]}.`);
      }
    });

    // 2. Verificar si el código y/o nombre ya existen
    const codigoLimpio = formData.codigo.trim().toUpperCase();
    const nombreLimpio = formData.nombrePuesto.trim().toUpperCase();

    const codigoExiste =
      codigoLimpio !== "" &&
      PUESTOS_EXISTENTES.some((p) => p.codigo.toUpperCase() === codigoLimpio);

    const nombreExiste =
      nombreLimpio !== "" &&
      PUESTOS_EXISTENTES.some(
        (p) => p.nombrePuesto.toUpperCase() === nombreLimpio
      );

    if (codigoExiste && nombreExiste) {
      yaExiste = true;
      mensajesError.push(
        "El código y el nombre del puesto ya existen. No se puede continuar hasta resolverlo."
      );
    } else if (codigoExiste) {
      yaExiste = true;
      mensajesError.push(
        "El código del puesto ya existe. No se puede continuar hasta resolverlo."
      );
    } else if (nombreExiste) {
      yaExiste = true;
      mensajesError.push(
        "El nombre del puesto ya existe. No se puede continuar hasta resolverlo."
      );
    }

    return { mensajesError, yaExiste };
  };

  const handleAceptar = (e) => {
    if (e && e.preventDefault) {
      e.preventDefault();
    }

    const { mensajesError, yaExiste } = validarFormulario();

    if (mensajesError.length > 0) {
      setErrores(mensajesError);
      setMostrarModal(false);

      if (yaExiste) {
        router.push("/gestionar-puestos");
      }
      return;
    }

    setErrores([]);
    setMostrarModal(true);
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
          Dar de alta puesto <br />
        </h2>
        <hr
          style={{
            width: "100%",
            border: "none",
            borderTop: "2px solid #1E2A1C",
            margin: "0.5rem 0rem 1.5rem 0rem",
            alignSelf: "stretch",
          }}
        />
        <div id="formulario">
          <form onSubmit={handleAceptar}>
            <div
              style={{
                display: "flex",
                gap: "30px",
                textAlign: "center",
                margin: "1rem 0",
                justifyContent: "center",
              }}
            >
              <InputMayus
                label="Codigo:   "
                placeholder="Codigo"
                value={formData.codigo}
                onChange={handleChange("codigo")}
              />
              <br />
              <InputMayus
                label="Nombre del puesto:   "
                placeholder="Nombre del puesto"
                value={formData.nombrePuesto}
                onChange={handleChange("nombrePuesto")}
              />
              <br />
            </div>
            <div
              style={{
                display: "flex",
                gap: "42px",
                textAlign: "center",
                margin: "1rem 0",
                justifyContent: "center",
              }}
            >
              <InputMayus
                label="Descripcion:   "
                placeholder="Descripcion"
                value={formData.descripcion}
                onChange={handleChange("descripcion")}
              />
              <br />
              <InputMayus
                label="Empresa:   "
                placeholder="Empresa"
                value={formData.empresa}
                onChange={handleChange("empresa")}
              />
              <br />
            </div>
            <InputMayus
              label="Caracteristicas del puesto:   "
              placeholder="Caracteristicas"
              value={formData.caracteristicas}
              onChange={handleChange("caracteristicas")}
            />
            <div
              style={{
                display: "flex",
                gap: "30px",
                textAlign: "center",
                margin: "1rem 0",
                justifyContent: "center",
              }}
            >
              <br />
            </div>
            <div
              style={{
                display: "flex",
                gap: "30px",
                textAlign: "center",
                margin: "1rem 0",
                justifyContent: "center",
              }}
            >
              <Boton
                type="submit"
                texto="Aceptar"
                onClick={handleAceptar}
                padding="10px 20px"
              />
              <Link href="/">
                <Boton texto="Cancelar" type="button" padding="10px 20px" />
              </Link>
            </div>
          </form>

          {errores.length > 0 && (
            <div
              role="alert"
              style={{
                color: "red",
                border: "1px solid red",
                padding: "8px 12px",
                marginTop: "12px",
                textAlign: "left",
                maxWidth: "560px",
                marginLeft: "auto",
                marginRight: "auto",
              }}
            >
              {errores.map((error, index) => (
                <div key={index}>{error}</div>
              ))}
            </div>
          )}
        </div>
      </div>

      {mostrarModal && (
        <div
          style={{
            position: "fixed",
            top: 0,
            left: 0,
            width: "100vw",
            height: "100vh",
            background: "rgba(0,0,0,0.5)",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            zIndex: 1000,
          }}
        >
          <div
            style={{
              background: "#f1f1f1",
              padding: "2rem",
              border: "1px solid #333",
              borderRadius: "4px",
              textAlign: "center",
              maxWidth: "400px",
            }}
          >
            <p>
              El puesto {formData.nombrePuesto} se ha creado correctamente ¿Desea
              cargar otro?
            </p>
            <div
              style={{
                display: "flex",
                gap: "1rem",
                justifyContent: "center",
                marginTop: "1.5rem",
              }}
            >
              <Link href="/">
                <Boton texto="NO" onClick={() => setMostrarModal(false)} />
              </Link>
              <Link href="/gestionar-puestos/dar-alta-puesto">
                <Boton texto="SI" onClick={() => setMostrarModal(false)} />
              </Link>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
