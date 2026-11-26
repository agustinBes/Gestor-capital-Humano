


type Props = {
  texto: string;
  onClick?: () => void;
  padding?: string;
  type?: "button" | "submit" | "reset";
  formaction?: string;
};


export default function Boton({texto, onClick, padding, type, formaction}: Props) {
  return (
    <button onClick={onClick} type={type} formAction={formaction}
    style={{
        padding: padding || "10px 20px",
        background: "#233622",
        color: "white",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer",
      }}>
      {texto}
    </button>
  );
}

