import { useNavigate } from "react-router-dom";

function InAppHeader() {
  const navigate = useNavigate();

  const userName = localStorage.getItem("userName");

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userName");
    navigate("/login");
  };

  return (
    <header style={{ 
      display: "flex", 
      justifyContent: "space-between", 
      alignItems: "center", 
      padding: "10px 20px", 
      borderBottom: "1px solid #ccc" 
    }}>
      <h1>Member Benefits</h1>
      <div>
        <span style={{ marginRight: "20px" }}>Welcome, {userName}!</span>
        <button onClick={handleLogout}>Logout</button>
      </div>
    </header>
  );
}

export default InAppHeader;
