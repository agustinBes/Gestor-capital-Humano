


type Props = {
  texto: string;
  onClick?: () => void;
  padding?: string;
  type?: "button" | "submit" | "reset";
  disabled?: boolean;
};


export default function Boton({texto, onClick, padding, type = "button", disabled = false}: Props) {
  return (
    <button type={type} onClick={onClick} disabled={disabled}
    style={{
        padding: padding || "10px 20px",
        background: disabled ? "#7a8378" : "#233622",
        color: "white",
        border: "none",
        borderRadius: "6px",
        cursor: disabled ? "not-allowed" : "pointer"
      }}>
      {texto}
    </button>
  );
}

