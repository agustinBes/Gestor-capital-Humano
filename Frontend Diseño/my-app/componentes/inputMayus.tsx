import type { ChangeEvent } from "react";

type Props = {
  label: string;
  placeholder?: string;
  value?: string;
  onChange?: (e: ChangeEvent<HTMLInputElement>) => void;
};


export default function InputMayus({label, placeholder, value,onChange}: Props) {
  return (
    <div>
      <label style={{ marginRight: "2px" }}>{label}</label>
      <input placeholder={placeholder}
      value={value ?? ""}
      onChange={onChange}
      style={{
    padding: "2px",
    border: "1px solid #1E2A1C",
    borderRadius: "4px",
    width: "200px",
    background: "#E0E4D3"
       }} />
    </div>
  );
}

