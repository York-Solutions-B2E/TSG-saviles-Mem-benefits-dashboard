import InAppHeader from "../components/InAppHeader";
import ActivePlan from "../components/ActivePlan";
import Accumulator from "../components/Accumulator";
import Claim from "../components/Claim";

function Dashboard() {
  return (
    <>
      <InAppHeader />
      <div style={{ padding: "1rem" }}>
        <h1>Dashboard</h1>
        <ActivePlan />
        <Accumulator />
        <Claim />
      </div>
    </>
  );
}

export default Dashboard;
