"use client";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Boton from "../../componentes/boton";
import Input from "../../componentes/input";
import Tabla from "../../componentes/tabla";

export default function EmitirReporteComparativo() {
  const router = useRouter();
  const [mostrarFunciones, setMostrarFunciones] = useState(false);
  const [paddingArriba, setPaddingArriba] = useState("17rem");

  // Paso 3: funciones que cumplen con los criterios (data de prueba)
  const [funciones] = useState([
    ["Analista de Sistemas", "Telecom"],
    ["Analista Funcional", "Telecom"],
    ["Líder de Proyecto", "Mercado Libre"],
    ["Desarrollador Senior", "Globant"],
  ]);

  // Paso 5: candidatos evaluados en la función (data de prueba)
  const [candidatos] = useState([
    ["Besigniano", "Lucas", "DNI", "40123456", "038", "12/09/2025"],
    ["Luna", "Agustina", "DNI", "41234567", "069", "15/09/2025"],
    ["Gaitan", "Micalea", "DNI", "39876543", "016", "18/09/2025"],
    ["Cabañas", "Ivan", "DNI", "42345678", "067", "20/09/2025"],
  ]);

  // Selección única (pasos 4 y 6)
  const [funcionSel, setFuncionSel] = useState<number | null>(null);
  const [candidatoSel, setCandidatoSel] = useState<number | null>(null);

  const seleccionarFuncion = (index: number) => {
    setFuncionSel(index);
    setCandidatoSel(null); // si cambia la función, se limpia el candidato
  };

  const seleccionarCandidato = (index: number) => {
    setCandidatoSel(index);
  };

  // Paso 6: presiona "Comparar"
  const comparar = () => {
    if (funcionSel === null || candidatoSel === null) return;
    const funcion = encodeURIComponent(String(funciones[funcionSel][0]));
    const nro = encodeURIComponent(String(candidatos[candidatoSel][4]));
    router.push(`/emitir-reporte-comparativo/emitir-reportecomparativo-pagina2?funcion=${funcion}&candidato=${nro}`);
  };

  useEffect(() => {
    const ajustarPadding = () => {
      if (window.innerWidth < 1400) {
        setPaddingArriba("4rem");
      } else {
        setPaddingArriba("17rem");
      }
    };
    ajustarPadding();
    window.addEventListener("resize", ajustarPadding);
    return () => window.removeEventListener("resize", ajustarPadding);
  }, []);

  return (
    <div style={{
      minHeight: "100vh",
      display: "flex",
      justifyContent: "center",
      alignItems: "flex-start",
      paddingTop: paddingArriba
    }}>
      <div className="flex flex-col flex-1" style={{
        display: "flex",
        maxWidth: mostrarFunciones ? "1100px" : "800px",
        margin: "2rem auto",
        border: "2px solid #1E2A1C",
        borderRadius: "8px",
        background: "#F5F5F5",
        alignItems: "center",
        width: "100%",
        paddingBottom: "2rem"
      }}>
        <h2 style={{ display: "flex", margin: "1rem auto 0.5rem", fontSize: "2rem" }}>
          <img src="/lupa.png" alt="Lupa" style={{ width: "30px", height: "30px", objectFit: "contain", marginRight: "0.5rem", marginTop: "0.5rem" }} />
          Emitir Reporte Comparativo
        </h2>

        <hr style={{
          width: "100%",
          border: "none",
          borderTop: "2px solid #1E2A1C",
          margin: "0.5rem 0rem 1.5rem 0rem",
          alignSelf: "stretch"
        }} />

        {/* Paso 2: filtros Nombre de Función y Empresa */}
        <div style={{ display: "flex", gap: "3rem" }}>
          <div>
            <Input label="Empresa" placeholder="Ingrese el nombre de la empresa" />
          </div>
          <div>
            <Input label="Nombre de función" placeholder="Ingrese el nombre de la función" />
          </div>
        </div>

        <div style={{ textAlign: "center", margin: "1rem" }}>
          <Boton
            texto="Buscar Función"
            onClick={() => { setMostrarFunciones(true); setPaddingArriba("1rem"); }}
            padding="20px 20px"
          />
        </div>

        {mostrarFunciones && (
          <div style={{ marginTop: "2rem", width: "100%" }}>
            <hr style={{
              width: "100%",
              border: "none",
              borderTop: "2px solid #1E2A1C",
              margin: "0.5rem 0rem 1.5rem 0rem",
              alignSelf: "stretch"
            }} />
          </div>
        )}

        {mostrarFunciones && (
          <div style={{ marginTop: "2rem" }}>
            {/* Pasos 3 y 4: grilla de funciones, el actor selecciona una */}
            <h3 style={{ fontSize: "1.5rem" }}>Funciones encontradas</h3>
            <Tabla
              columnas={["Nombre de función", "Empresa"]}
              datos={funciones}
              onRowClick={seleccionarFuncion}
              seleccionados={funcionSel !== null ? [funcionSel] : []}
            />

            {/* Paso 5: candidatos evaluados en la función seleccionada */}
            {funcionSel !== null && (
              <div style={{ marginTop: "2rem" }}>
                <h3 style={{ fontSize: "1.5rem" }}>Candidatos evaluados</h3>
                <Tabla
                  columnas={[
                    "Apellido",
                    "Nombre",
                    "Tipo de documento",
                    "Documento",
                    "Nro de Candidato",
                    "Fecha de finalización",
                  ]}
                  datos={candidatos}
                  onRowClick={seleccionarCandidato}
                  seleccionados={candidatoSel !== null ? [candidatoSel] : []}
                />

                {/* Paso 6: botón Comparar */}
                <div style={{ textAlign: "center", margin: "1.5rem" }}>
                  <Boton texto="Comparar" onClick={comparar} padding="10px 30px" />
                </div>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}