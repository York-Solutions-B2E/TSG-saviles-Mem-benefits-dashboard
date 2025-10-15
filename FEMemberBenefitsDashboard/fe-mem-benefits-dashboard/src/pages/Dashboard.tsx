import InAppHeader from "../components/InAppHeader";
import ActivePlan from "../components/ActivePlan";
import Accumulator from "../components/Accumulator";

function Dashboard() {
  return (
    <>
      <InAppHeader />
      <div style={{ padding: "1rem" }}>
        <h1>Dashboard</h1>
        <ActivePlan />
        <Accumulator />
      </div>
    </>
  );
}

export default Dashboard;
