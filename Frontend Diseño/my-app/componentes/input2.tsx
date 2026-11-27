import React from "react";

type Props = {
  label: string;
  placeholder?: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  name?: string;
};

export default function Input2({ label, placeholder, value, onChange, name }: Props) {
  return (
    <div>
      <label style={{ marginRight: "2px" }}>{label}</label>
      <input
        name={name}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        style={{
          padding: "2px",
          border: "1px solid #1E2A1C",
          borderRadius: "4px",
          width: "200px",
          background: "#E0E4D3",
        }}
      />
    </div>
  );
}
