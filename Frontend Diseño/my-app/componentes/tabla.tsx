"use client"

type Props = {
  columnas: string[];
  datos: (string | number)[][];
  onRowClick?: (index: number) => void;
  seleccionados?: number[];
};

export default function Tabla({ columnas, datos, onRowClick, seleccionados = []}: Props) {
  return (
    <table style={{ borderCollapse: "collapse", width: "100%", marginTop: "1rem" }}>
        <thead>
            <tr>
                {columnas.map((columna, index) => (
            <th
              key={index}
              style={{
                border: "1px solid #1E2A1C",
                padding: "10px",
                background: "#BECFAB",
                textAlign: "left"
              }}
            >
              {columna}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {datos.map((fila, i) => (
          <tr key={i}
          onClick={() => onRowClick && onRowClick(i)}
            style={{
              background: seleccionados.includes(i) ? "#EAE0B5" : "white",
              cursor: onRowClick ? "pointer" : "default"
            }}>
            {fila.map((celda, j) => (
              <td
                key={j}
                style={{
                  border: "1px solid #1E2A1C",
                  padding: "10px"
                }}
              >
                {celda}
              </td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}