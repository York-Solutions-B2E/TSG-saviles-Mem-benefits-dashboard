function ClaimNumberFilter({ claimNumber, setClaimNumber }: any) {
  return (
    <div
      style={{
        display: "inline-flex",
        alignItems: "center",
        border: "1px solid black",
        padding: "16px",
        width: "auto",
        borderRadius: "4px",
        verticalAlign: "top",
        marginLeft: "16px",
      }}
    >
      <label>
        Claim #:
        <input
          type="text"
          value={claimNumber} //Controlled input. Displays the parent state 'claimNumber'
          onChange={(e) => setClaimNumber(e.target.value)} //Updates parent state via 'setClaimNumber' when user types
          placeholder="Claim #"
          style={{ padding: "4px", marginLeft: "8px" }}
        />
      </label>
    </div>
  );
}

export default ClaimNumberFilter;
