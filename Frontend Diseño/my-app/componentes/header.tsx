import Image from "next/image";
import Link from "next/link";

export default function Header() {
  return (
  <header style={{ background: "#E7EEE6", color: "#1E2A1C", display: "flex", gap: "3px", alignItems: "center",position: "relative"}}>
   <Link href="/" aria-label="Ir al inicio" style={{
      display: "flex",
      alignItems: "center",
      gap: "0.5rem",
      margin: "0 auto",
      color: "inherit",
      textDecoration: "none"
    }}>
      <Image src="/logo.png" alt="" width={50} height={50} style={{ width: "clamp(30px, 4vw, 50px)", height: "clamp(30px, 4vw, 50px)", objectFit: "contain"}} />
      <h1 style={{fontSize: "clamp(1.4rem, 3.5vw, 3.4rem)"}}> C@pital Humano</h1>
    </Link>
   <Image src="/icoperfil.png" alt="Usuario" width={50} height={50} style={{ width: "clamp(30px, 4vw, 50px)", height: "clamp(30px, 4vw, 50px)", objectFit: "contain",position: "absolute",
      right: "1.5rem"}} />

     </header>

)


}