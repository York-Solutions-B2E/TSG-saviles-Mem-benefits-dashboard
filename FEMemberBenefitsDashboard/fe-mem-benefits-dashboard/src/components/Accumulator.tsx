import { useEffect, useState } from "react";

function Accumulator() {
    const [data, setData] = useState<any>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = localStorage.getItem("token");
                const response = await fetch("http://localhost:8080/api/dashboard", {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    }
                });
                const json = await response.json;
                setData(json);
            } catch (error) {
                console.error("Error fetching accumulator data:", error);
            }
        }
        fetchData();
    }, []);

    if(!data) {
        return <p>No Accumulator Data</p>
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
      <h2>Accumulator:</h2>
      <div style={{ marginBottom: "1rem" }}>
        <strong>Deductable:</strong> data<br />
      </div>
    </div>
    )
}

export default Accumulator;