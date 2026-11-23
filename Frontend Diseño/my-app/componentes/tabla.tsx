"use client"

type Props = {
  columnas: string[];
  datos: (string | number)[][];
};

export default function Tabla({ columnas, datos }: Props) {
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
          <tr key={i}>
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