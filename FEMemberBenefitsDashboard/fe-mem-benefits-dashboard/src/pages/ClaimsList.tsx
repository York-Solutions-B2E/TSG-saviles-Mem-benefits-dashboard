import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import InAppHeader from "../components/InAppHeader";
import StatusFilter from "../assets/StatusFilter";

function ClaimsList() {
  const [claims, setClaims] = useState<any>(null);
  const navigate = useNavigate();

  // Get the statuses from statusfilter component
  const [selectedStatuses, setSelectedStatuses] = useState<boolean[]>([false, false, false, false, false]);
  const statusNames = ["SUBMITTED", "IN_REVIEW", "PROCESSED", "PAID", "DENIED"];

  // Fetch claims whenever the selectedStatuses change
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Build query params
        const params = new URLSearchParams({ page: "0", size: "10" });

        // Add each checked status as a param
        statusNames.forEach((status, i) => {
          if (selectedStatuses[i]) {
            params.append("status", status); 
          }
        });

        const token = localStorage.getItem("token");
        const response = await fetch(
          `http://localhost:8080/api/claims/allclaims?${params.toString()}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        const json = await response.json();
        setClaims(json);
      } catch (error) {
        console.error("Error fetching claim details data:", error);
      }
    };

    fetchData();
  }, [selectedStatuses]);

  if (!claims) return <p>Loading claims...</p>;

  // Build claim cards
  const claimsList = [];
  for (let i = 0; i < claims.content.length; i++) {
    const claim = claims.content[i];
    claimsList.push(
      <div key={i} style={{ border: "1px solid black", margin: "8px", padding: "8px" }}>
        <p><strong>Claim #:</strong> {claim.claimNumber}</p>
        <p><strong>Provider:</strong> {claim.providerName}</p>
        <p><strong>Service Start Date:</strong> {new Date(claim.serviceStartDate).toLocaleDateString("en-US")}</p>
        <p><strong>Service End Date:</strong> {new Date(claim.serviceEndDate).toLocaleDateString("en-US")}</p>
        <p><strong>Status:</strong> {claim.status}</p>
        <p><strong>Member Responsible:</strong> ${claim.totalMemberResponsibility}</p>
        <button
          style={{ marginTop: "16px", padding: "8px 12px", cursor: "pointer" }}
          onClick={() => navigate(`/claims/${claim.id}`)}
        >
          View Details
        </button>
      </div>
    );
  }

  return (
    <>
      <InAppHeader />
      <StatusFilter
        selectedStatuses={selectedStatuses}
        setSelectedStatuses={setSelectedStatuses}
      />
      <div>All Claims:</div>
      <div>{claimsList}</div>
      <button
        style={{ marginTop: "16px", padding: "8px 12px", cursor: "pointer" }}
        onClick={() => navigate("/dashboard")}
      >
        Return to Dashboard
      </button>
    </>
  );
}

export default ClaimsList;
