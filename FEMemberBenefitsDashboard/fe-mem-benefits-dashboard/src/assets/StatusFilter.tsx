
//React functinal component. Tkes 2 props
function StatusFilter({ selectedStatuses, setSelectedStatuses }: any) {
  const statusNames = ["Submitted", "In Review", "Processed", "Paid", "Denied"];
    //When checkbox is clicked, calls this function w/ an idex of the clicked checkbox
  const handleOnChange = (index: number) => {
    const newStates = [...selectedStatuses]; //Creates copy of current checkbox states. Creates another reference to the array
    newStates[index] = !newStates[index];
    setSelectedStatuses(newStates);
  };

  return (
    <div
      style={{
        border: "1px solid black",
        padding: "16px",
        width: "200px",
        borderRadius: "4px",
        marginBottom: "16px",
      }}
    >
      <p>Status:</p>
      {statusNames.map((status, i) => ( //Loops through all statuses to render a checkbox for each
        <div key={i}>
          <label>
            <input
              type="checkbox"
              checked={selectedStatuses[i]} //check or no check status
              onChange={() => handleOnChange(i)}
            />
            {status}
          </label>
        </div>
      ))}
    </div>
  );
}

export default StatusFilter;
