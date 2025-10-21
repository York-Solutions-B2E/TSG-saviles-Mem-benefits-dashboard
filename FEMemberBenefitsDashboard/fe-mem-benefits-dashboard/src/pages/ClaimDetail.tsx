import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import InAppHeader from "../components/InAppHeader";

function ClaimDetail() {
    const navigate = useNavigate();
    const {claimId} = useParams();
    const [claim, setClaim] = useState<any>(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const token = localStorage.getItem("token");
                const response = await fetch(`http://localhost:8080/api/claims/${claimId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                });
                const json = await response.json();
                setClaim(json);
            } catch (error) {
                console.error("Error fetching claim details data:", error);
            }
        }
        fetchData();
    }, []);

    console.log(claim)

    if(!claim) {
        return <p>No claim details Data</p>
    }

    const claimInfo = (
        <div style={{ border: "1px solid black", margin: "8px", padding: "8px" }}>
            <p><strong>Claim #:</strong> {claim.claimNumber}</p>
            <p><strong>Status:</strong> {claim.status}</p>
            <p><strong>Provider:</strong> {claim.providerName}</p>
            <p><strong>Service Start:</strong> {claim.serviceStartDate}</p>
            <p><strong>Service End:</strong> {claim.serviceEndDate}</p>
            <p><strong>Received Date:</strong> {claim.recievedDate}</p>
        </div>
    );

    const financialSummary = (
        <div style={{ border: "1px solid black", margin: "8px", padding: "8px" }}>
            <p><strong>Total Billed:</strong> ${claim.totalBilled.toFixed(2)}</p>
            <p><strong>Total Allowed:</strong> ${claim.totalAllowed.toFixed(2)}</p>
            <p><strong>Plan Paid:</strong> ${claim.totalPlanPaid.toFixed(2)}</p>
            <p><strong>Member Responsibility:</strong> ${claim.totalMemberResponsibility.toFixed(2)}</p>
        </div>
    );

    let claimLinesList = [];

    for (let i = 0; i < claim.claimLines.length; i++) {
        const line = claim.claimLines[i];

        const element = (
            <div key={i} style={{ border: "1px solid gray", margin: "4px", padding: "4px" }}>
                <p><strong>CPT Code:</strong> {line.cptCode}</p>
                <p><strong>Description:</strong> {line.description}</p>
                <p><strong>Billed:</strong> ${line.billedAmount.toFixed(2)}</p>
                <p><strong>Allowed:</strong> ${line.allowedAmount.toFixed(2)}</p>
                <p><strong>Deductible Applied:</strong> ${line.deductibleApplied.toFixed(2)}</p>
                <p><strong>Copay Applied:</strong> ${line.copayApplied.toFixed(2)}</p>
                <p><strong>Coinsurance Applied:</strong> ${line.coinsuranceApplied.toFixed(2)}</p>
                <p><strong>Plan Paid:</strong> ${line.planPaid.toFixed(2)}</p>
                <p><strong>Member Responsibility:</strong> ${line.memberResponsibility.toFixed(2)}</p>
            </div>
        );

    claimLinesList.push(element);
    }
   let claimStatusEventsList = [];

   for (let i = 0; i < claim.claimStatusEvents.length; i++) {
        const event = claim.claimStatusEvents[i];
        const dateObj = new Date(event.occurredAt);
        const formattedDate = dateObj.toLocaleDateString('en-US');
        const formattedTime = dateObj.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit' });

        const element = (
            <div key={i} style={{ border: "1px solid gray", margin: "4px", padding: "4px" }}>
                <p><strong>{event.status}</strong>: {formattedDate} {formattedTime}</p>
            </div>
        );

    claimStatusEventsList.push(element);
    }

    return (
        <>
        <InAppHeader />
        <div>
            <h3>Claim Details:</h3>
            {claimInfo}
            <h3>Status History:</h3>
            {claimStatusEventsList}
            <h3>Financial Summary:</h3>
            {financialSummary}
            <h3>Claim Lines:</h3>
            {claimLinesList}
            <button 
                style={{ marginTop: "16px", padding: "8px 12px", cursor: "pointer" }} 
                onClick={() => navigate("/dashboard")}
                >Return to Dashboard
            </button>
            <button 
                style={{ marginTop: "16px", padding: "8px 12px", cursor: "pointer" }} 
                onClick={() => navigate("/claimslist")}
                >View all Claims
            </button>
        </div>
        </>
    )
}

export default ClaimDetail;