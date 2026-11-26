"use client";

type Props = {
  label: string;
  opciones: string[];
  valor: string;
  onChange: (valor: string) => void;
};

export default function Desplegable({ label, opciones, valor, onChange }: Props) {
  return (
    <div style={{ marginBottom: "1rem" }}>
      <label style={{ display: "block", marginBottom: "0.5rem", fontSize: "1.1rem"  }}>
        {label}
      </label>
      <select
        value={valor}
        onChange={(e) => onChange(e.target.value)}
        style={{
          padding: "8px",
          border: "1px solid #ccc",
          borderRadius: "4px",
          width: "250px",
          fontSize: "1rem"
        }}
      >
        <option value="">Funcion/Puesto</option>
        {opciones.map((opcion, index) => (
          <option key={index} value={opcion}>
            {opcion}
          </option>
        ))}
      </select>
    </div>
  );
}