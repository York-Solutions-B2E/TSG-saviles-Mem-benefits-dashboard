import { useEffect, useState } from "react";
import InAppHeader from "../components/InAppHeader";

function Dashboard() {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token"); // adjust if you store it elsewhere
        const response = await fetch("http://localhost:8080/api/dashboard", {
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });

        const json = await response.json();
        setData(json);
      } catch (error) {
        console.error("Error fetching dashboard data:", error);
      }
    };

    fetchData();
  }, []);

  return (
    <>
    <InAppHeader />
    <div>
      <h1>Dashboard</h1>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
    </>
  );
}

export default Dashboard;
