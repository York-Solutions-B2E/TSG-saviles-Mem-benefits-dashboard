function ResultsPerPageFilter({ resultSize, setResultSize }: any) {
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
        Results per page:{" "}
        <select
          value={resultSize}
          onChange={(e) => setResultSize(Number(e.target.value))}
          style={{ padding: "4px", marginLeft: "8px" }}
        >
          <option value={10}>10</option>
          <option value={25}>25</option>
        </select>
      </label>
    </div>
  );
}

export default ResultsPerPageFilter;
