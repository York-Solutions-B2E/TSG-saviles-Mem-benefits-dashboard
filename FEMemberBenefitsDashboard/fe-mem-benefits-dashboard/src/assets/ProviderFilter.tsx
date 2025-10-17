function ProviderFilter({ provider, setProvider }: any) {
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
        Provider:{" "}
        <input
          type="text"
          value={provider}
          onChange={(e) => setProvider(e.target.value)}
          placeholder="Provider"
          style={{ padding: "4px", marginLeft: "8px" }}
        />
      </label>
    </div>
  );
}

export default ProviderFilter;
