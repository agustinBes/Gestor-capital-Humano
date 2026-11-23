


type Props = {
  texto: string;
  onClick: () => void;
  padding?: string;
};


export default function Boton({texto, onClick, padding}: Props) {
  return (
    <button onClick={onClick} 
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

