import InAppHeader from "../components/InAppHeader";
import ActivePlan from "../components/ActivePlan";
import Accumulator from "../components/Accumulator";
import Claim from "../components/Claim";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const navigate = useNavigate();
  return (
    <>
      <InAppHeader />
      <div style={{ padding: "1rem" }}>
        <h1>Dashboard</h1>
        <ActivePlan />
        <Accumulator />
        <Claim />
      </div>
      <button 
        style={{ marginTop: "16px", padding: "8px 12px", cursor: "pointer" }} 
        onClick={() => navigate("/claimslist")}
        >View All Claims
       </button>
    </>
  );
}

export default Dashboard;
