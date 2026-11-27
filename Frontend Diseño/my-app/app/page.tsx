import Link from "next/link";

const accesos = [
  {
    href: "/gestionar-puestos",
    titulo: "Gestionar puestos",
    descripcion: "Buscar puestos existentes o iniciar el alta de un puesto.",
  },
  {
    href: "/gestionar-preguntas",
    titulo: "Gestionar preguntas",
    descripcion: "Buscar preguntas o iniciar el alta de una pregunta.",
  },
  {
    href: "/evaluar-candidato",
    titulo: "Evaluar candidatos",
    descripcion: "Buscar candidatos y comenzar el flujo de evaluación.",
  },
  {
    href: "/completar-cuestionario",
    titulo: "Completar cuestionario",
    descripcion: "Leer las instrucciones y responder el cuestionario disponible.",
  },
  {
    href: "/emitir-reporte-comparativo",
    titulo: "Reporte comparativo",
    descripcion: "Seleccionar una función y un candidato para consultar el reporte.",
  },
  {
    href: "/emitir-orden-merito",
    titulo: "Orden de mérito",
    descripcion: "Buscar puestos y comenzar la emisión de una orden de mérito.",
  },
];

export default function Inicio() {
  return (
    <main
      style={{
        width: "min(1100px, calc(100% - 2rem))",
        margin: "3rem auto",
        flex: 1,
      }}
    >
      <header style={{ marginBottom: "2rem", textAlign: "center" }}>
        <h2 style={{ margin: "0 0 0.5rem", fontSize: "2rem" }}>
          Gestión de evaluaciones
        </h2>
        <p style={{ margin: 0, fontSize: "1.1rem" }}>
          Seleccioná una de las funciones disponibles para comenzar.
        </p>
      </header>

      <nav
        aria-label="Funciones disponibles"
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(min(100%, 280px), 1fr))",
          gap: "1rem",
        }}
      >
        {accesos.map(({ href, titulo, descripcion }) => (
          <Link
            key={href}
            href={href}
            style={{
              display: "block",
              padding: "1.25rem",
              border: "2px solid #1E2A1C",
              borderRadius: "8px",
              background: "#F5F5F5",
              color: "#1E2A1C",
              textDecoration: "none",
            }}
          >
            <h3 style={{ margin: "0 0 0.5rem", fontSize: "1.25rem" }}>
              {titulo}
            </h3>
            <p style={{ margin: 0 }}>{descripcion}</p>
          </Link>
        ))}
      </nav>
    </main>
  );
}
