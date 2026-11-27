


type Props = {
  texto: string;
  onClick?: () => void;
  padding?: string;
  type?: "button" | "submit" | "reset";
};


export default function Boton({texto, onClick, padding, type = "button"}: Props) {
  return (
    <button type={type} onClick={onClick}
    style={{
        padding: padding || "10px 20px",
        background: "#233622",
        color: "white",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer"
      }}>
      {texto}
    </button>
  );
}

