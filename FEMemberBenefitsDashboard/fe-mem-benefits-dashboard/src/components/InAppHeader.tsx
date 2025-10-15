import { useNavigate } from "react-router-dom";

function InAppHeader() {
  const navigate = useNavigate();

  // Grab user info from localStorage (assuming you stored name after login)
  const userName = localStorage.getItem("userName") || "User";

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userName"); // optional, clean up stored name
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
      <h1>Member Benefits Dashboard</h1>
      <div>
        <span style={{ marginRight: "20px" }}>Welcome, {userName}!</span>
        <button onClick={handleLogout}>Logout</button>
      </div>
    </header>
  );
}

export default InAppHeader;
