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


    if(!data) {
        return <p>No Claims data</p>
    }

    let claimsList = [];

    for (let i = 0;i<data.length; i++) {
        const claim = data[i]

        const element = (
            <div key={i} style={{ border: "1px solid black", margin: "8px", padding: "8px" }}>
                <p><strong>Claim #:</strong> {claim.claimNumber}</p>
                <p><strong>Status:</strong> {claim.status}</p>
                <p><strong>Member Responsible:</strong> ${claim.totalMemberResponsibility}</p>
            </div>
        )
        claimsList.push(element)
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
      <h2>Claims:</h2>
        <div style={{ marginBottom: "1rem" }}>
        <strong>Deductable:</strong> {claimsList}<br />
      </div>
    </div>
    )
}


export default Claim;