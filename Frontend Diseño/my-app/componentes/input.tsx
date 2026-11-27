type Props = {
  label: string;
  placeholder?: string;
};


export default function Input({label, placeholder}: Props) {
  return (
    <div>
      <label style={{ marginRight: "2px" }}>{label}</label>
      <input placeholder={placeholder} 
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