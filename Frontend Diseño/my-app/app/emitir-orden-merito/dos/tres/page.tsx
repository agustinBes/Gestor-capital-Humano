"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Boton from "../../../../componentes/boton";
import Tabla from "../../../../componentes/tabla";

export default function CompletarCuestionario() {
    const router = useRouter();

    const [datos, setDatos] = useState([
        ["038"],
        ["069"],
        ["016"],
        ["067"],
    ]);

    const [seleccionados, setSeleccionados] = useState<number[]>([]);

    const seleccionDeFilas = (index: number) => {
        if (seleccionados.includes(index)) {
            setSeleccionados(seleccionados.filter((i) => i !== index));
        } else {
            setSeleccionados([...seleccionados, index]);
        }
    };

    const seleccionarTodas = () => {
        if (seleccionados.length === datos.length) {
            setSeleccionados([]);
        } else {
            setSeleccionados(datos.map((_, index) => index));
        }
    };

    const handleEmitirOrden = () => {
        if (seleccionados.length === 0) {
            alert("Debe seleccionar al menos una evaluación para emitir la orden de mérito.");
            return;
        }

        router.push("/emitir-orden-merito/dos/tres/cuatro");
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
                        margin: "0.5rem 0rem 1.5rem 0rem",
                        alignSelf: "stretch",
                    }}
                />
                Seleccione para que evaluación/es se decide emitir el orden de mérito:
                <div id="Tabla">
                    <div style={{ display: "flex", gap: "50px", textAlign: "center", margin: "1rem 0" }}>
                        <Tabla
                            columnas={["Evaluaciones"]}
                            datos={datos}
                            onRowClick={seleccionDeFilas}
                            seleccionados={seleccionados}
                        />
                    </div>
                </div>

                <div style={{ display: "flex", gap: "100px", textAlign: "center", margin: "1rem 0" }}>
                    <Boton
                        texto="Seleccionar Todas"
                        onClick={seleccionarTodas}
                        padding="10px 20px"
                    />
                    <Boton
                        texto="Emitir orden"
                        type="button"
                        onClick={handleEmitirOrden}
                        padding="10px 20px"
                    />
                </div>
            </div>
        </div>
    );
}
