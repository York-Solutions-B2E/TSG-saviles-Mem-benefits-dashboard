function DateFilter({ startDate, setStartDate, endDate, setEndDate }: any) {
  return (
    <div style={{
            display: "inline-flex",
            alignItems: "center",
            border: "1px solid black",
            padding: "16px",
            width: "auto",
            borderRadius: "4px",
            verticalAlign: "top",
      }}>
      <label>
        Start Date:{" "}
        <input
          type="date"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
          style={{ padding: "4px", marginRight: "16px" }}
        />
      </label>

      <label>
        End Date:{" "}
        <input
          type="date"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
          style={{ padding: "4px" }}
        />
      </label>
    </div>
  );
}

export default DateFilter;
