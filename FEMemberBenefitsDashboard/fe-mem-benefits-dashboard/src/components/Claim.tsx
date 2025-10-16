import { useEffect, useState } from "react";

function Claim() {

    const [data, setData] = useState<any>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = localStorage.getItem("token");
                const reponse = await fetch("http://localhost:8080/api/dashboard/claims", {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    }
                });
                const json = await reponse.json();
                setData(json)
                
            } catch (error) {
                console.error("Error fetching claims data:", error);
            }
        }
        fetchData();
    },[]);

    return (
    <div
      style={{
        border: "1px solid #ddd",
        padding: "1rem",
        borderRadius: "8px",
        maxWidth: "400px",
      }}
    >
      <h2>Claims:</h2>

    </div>
    )
}


export default Claim;