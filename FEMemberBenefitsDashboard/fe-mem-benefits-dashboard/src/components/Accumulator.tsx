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
          }
        });
                
        const json = await response.json();
        setData(json);
      } catch (error) {
        console.error("Error fetching accumulator data:", error);
      }
    };
        
    fetchData();
  }, []);

  if(!data) {
    return <p>No Accumulator Data</p>
  }


  let accumulatorItems = [];

  for (let i = 0; i < data.length; i++) {
    const acc = data[i];

    const type = acc.accumulatorType.charAt(0).toUpperCase() + acc.accumulatorType.slice(1).toLowerCase();

    // Creating an element to return later
    const element = (
      <div key={i}> {/*Helps react keep track of which data element we are referring to*/}
        <p><strong>{type}:</strong> ${acc.usedAmount.toFixed(2)}/${acc.limitAmount.toFixed(2)}</p>
      </div>
    );

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
      <div>
       {accumulatorItems}
      </div>
    </div>
    )
}

export default Accumulator;