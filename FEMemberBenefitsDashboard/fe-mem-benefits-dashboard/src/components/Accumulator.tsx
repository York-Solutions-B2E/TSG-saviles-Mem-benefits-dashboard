import { useEffect, useState } from "react";

function Accumulator() {
    const [data, setData] = useState<any>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = localStorage.getItem("token");
                const response = await fetch("http://localhost:8080/api/dashboard/accumulator", {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    }
                });
                const json = await response.json();
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

    

    let accumulatorItems = [];

  // loop through the data
  for (let i = 0; i < data.length; i++) {
    const acc = data[i];

    const type = acc.accumulatorType.charAt(0).toUpperCase() + acc.accumulatorType.slice(1).toLowerCase();

    // create a simple element for each accumulator
    const element = (
      <div key={i}>
        <p><strong>{type}:</strong> ${acc.usedAmount.toFixed(2)}/${acc.limitAmount.toFixed(2)}</p>
        <hr />
      </div>
    );

    // add it to the array
    accumulatorItems.push(element);
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
       {accumulatorItems}<br />
      </div>
    </div>
    )
}

export default Accumulator;