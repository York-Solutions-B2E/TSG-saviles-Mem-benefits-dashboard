import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import InAppHeader from "../components/InAppHeader";
import StatusFilter from "../assets/StatusFilter";
import DateFilter from "../assets/DateFilter";
import ProviderFilter from "../assets/ProviderFilter";
import ClaimNumberFilter from "../assets/ClaimNumberFilter";
import ResultsPerPageFilter from "../assets/ResultsPerPageFilter";

function ClaimsList() {
  const [claims, setClaims] = useState<any>(null);
  const [page, setPage] = useState(0); 
  const [hasMore, setHasMore] = useState(true);
  const navigate = useNavigate();

  // Get filters from child components
  const [selectedStatuses, setSelectedStatuses] = useState<boolean[]>([false, false, false, false, false]); //Holds the status state
  const statusNames = ["SUBMITTED", "IN_REVIEW", "PROCESSED", "PAID", "DENIED"];
  const [startDate, setStartDate] = useState<string>("");
  const [endDate, setEndDate] = useState<string>("");
  const [provider, setProvider] = useState("");
  const [claimNumber, setClaimNumber] = useState<string>("");
  const [resultSize, setResultSize] = useState<any>(10);

  // Fetch claims whenever filters change
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Build query params
        const params = new URLSearchParams({ page: page.toString()}); //browser API that allows you to build query string

        // Add each checked status as a param
        statusNames.forEach((status, i) => {
          if (selectedStatuses[i]) {
            params.append("status", status);
          }
        });

        // Add each filter as a param
        if (startDate) {
          params.append("startDate", startDate)
        }
        if (endDate) {
          params.append("endDate", endDate)
        }
        if (provider) {
          params.append("provider", provider)
        }
        if (claimNumber) {
          params.append("claimNumber", claimNumber)
        }
        if (resultSize) {
          params.append("size", resultSize)
        }

        const token = localStorage.getItem("token");
        const response = await fetch(
          `http://localhost:8080/api/claims/allclaims?${params.toString()}`,
          {
            headers: {
              Authorization: `Bearer ${token}`
            },
          }
        );

        const json = await response.json();
        setClaims(json);
        setHasMore(page + 1 < json.totalPages); //true until we exceed total pages
      } catch (error) {
        console.error("Error fetching claim details data:", error);
      }
    };

    fetchData();
  }, [selectedStatuses, page, startDate, endDate, provider, claimNumber, resultSize]); 

  if (!claims) {
    return <p>No claim Data</p>;
  }

  // Build claim boxes
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
        selectedStatuses={selectedStatuses} //Passing parent state down as props
        setSelectedStatuses={setSelectedStatuses} // Passing function to update parent state
      />
      <DateFilter
        startDate={startDate}
        setStartDate={setStartDate}
        endDate={endDate}
        setEndDate={setEndDate}
      />
      <ProviderFilter 
        provider={provider}
        setProvider={setProvider}
      />
      <ClaimNumberFilter 
        claimNumber={claimNumber}
        setClaimNumber={setClaimNumber}
      />
      <ResultsPerPageFilter 
        resultSize = {resultSize}
        setResultSize = {setResultSize}
      />
      <div>All Claims:</div>
      <div>{claimsList}</div>

      <div style={{ marginTop: "16px" }}>
        <button onClick={() => setPage(page - 1)} disabled={page === 0}>
          Previous Page
        </button>
        <span style={{ margin: "8px" }}>Page {page + 1}</span>
        <button onClick={() => setPage(page + 1)}disabled={!hasMore}>
          Next Page
        </button>
      </div>

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
