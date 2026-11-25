export default function Header() {
  return (
  <header style={{ background: "#E7EEE6", color: "#1E2A1C", display: "flex", gap: "3px", alignItems: "center"}}>
   <img src="/logo.png" alt="Logo" style={{ width: "35px", height: "35px", objectFit: "contain"}} />
   <h1 style={{fontSize: "2rem"}}> C@pital Humano</h1>
   <img src="/icoperfil.png" alt="Usuario" style={{ width: "35px", height: "35px", objectFit: "contain",marginLeft: "auto"}} />
  </header>

)


}