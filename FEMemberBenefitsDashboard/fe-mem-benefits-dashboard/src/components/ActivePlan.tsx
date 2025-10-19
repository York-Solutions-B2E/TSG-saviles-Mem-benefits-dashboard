import { useEffect, useState } from "react";

function ActivePlan() {
  const [data, setData] = useState<any>(null); //Instead of any, could use an interface to model data.

  useEffect(() => {
    const fetchData = async () => { // We want async. useEffect does not like. So, we create a function, and then call it below.
      try {
        const token = localStorage.getItem("token");
        const response = await fetch("http://localhost:8080/api/dashboard/enrollment", {
          headers: {
            Authorization: `Bearer ${token}`,
          }
        });

        const json = await response.json();
        setData(json);
      } catch (error) {
        console.error("Error fetching active plan:", error);
      } 
    };

    fetchData(); 
  }, []);

  if (!data) {
    return <p>No active enrollment found.</p>;
  }

  return (
    <div
      style={{
        border: "1px solid #ddd",
        padding: "1rem",
        borderRadius: "8px",
        maxWidth: "400px",
      }}
    >
      <h2>Active Plan:</h2>
      <div>
        <strong>Plan Name:</strong> {data.plan.name} <br />
        <strong>Plan Type:</strong> {data.plan.type} <br />
        <strong>Network:</strong> {data.plan.networkName} <br />
        <strong>Plan Year:</strong> {data.plan.planYear} <br />
        <strong>Coverage Start: </strong>
        {new Date(data.coverageStart).toLocaleDateString("en-US")} <br />
        <strong>Coverage End: </strong>
        {new Date(data.coverageEnd).toLocaleDateString("en-US")}
      </div>
    </div>
  );
}

export default ActivePlan;
