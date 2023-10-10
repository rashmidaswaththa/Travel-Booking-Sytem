import React, { useState, useEffect } from "react";

function AddSchedule() {
  const [trainData, setTrainData] = useState([]);
  const [scheduleData, setScheduleData] = useState({
    id: "",
    trainId: "",
    fromLocation: "",
    toLocation: "",
    departureTime: "", // Keep it as a string initially
    arrivalTime: "",
    scheduleDate: "",
  });

  useEffect(() => {
    // Fetch the list of available trains (trainId and trainName) from the server
    async function fetchTrainData() {
      try {
        const response = await fetch("/api/trains");
        if (response.ok) {
          const trainData = await response.json();
          setTrainData(trainData);
        } else {
          console.error("Failed to fetch train data.");
        }
      } catch (error) {
        console.error("Error:", error);
      }
    }

    fetchTrainData();
  }, []);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setScheduleData({
      ...scheduleData,
      [name]: value,
    });
  };

  const isFutureDate = (dateString) => {
    const currentDate = new Date();
    const selectedDate = new Date(dateString);
    return selectedDate > currentDate;
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Check if scheduleDate is a future date
    if (!isFutureDate(scheduleData.scheduleDate)) {
      alert("Schedule Date should be a future date.");
      return;
    }

    // Format the departureTime to ISO string format
    const formattedDepartureTime = new Date(`1970-01-01T${scheduleData.departureTime}:00`).toISOString();

    // Update the scheduleData with the formatted departureTime
    setScheduleData({
      ...scheduleData,
      departureTime: formattedDepartureTime,
    });

    // Log the scheduleData before sending the request
    console.log("Submitting scheduleData:", scheduleData);

    try {
      // Serialize the trainData object to JSON
      const requestBody = JSON.stringify(scheduleData);

      // Send POST request to your API endpoint with the "Content-Type" header
      const response = await fetch("/api/schedules", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: requestBody, // Send the serialized JSON data
      });

      if (response.ok) {
        // Schedule created successfully
        console.log("Schedule created successfully");
        // Redirect the user or perform other actions
      } else if (response.status === 400) {
        // Handle validation errors or other client-side errors
        const errorData = await response.json();
        console.error("Failed to create schedule:", errorData);

        // Display validation error messages to the user
        if (errorData.errors) {
          for (const errorKey in errorData.errors) {
            console.error(`Validation error for ${errorKey}: ${errorData.errors[errorKey]}`);
          }
        }
      } else {
        // Handle other errors (e.g., server errors)
        console.error("Failed to create schedule");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div>
      <h1>Add Schedule</h1>
      <form onSubmit={handleSubmit} method="POST">
        <label htmlFor="trainId">Train:</label>
        <select
          id="trainId"
          name="trainId"
          value={scheduleData.trainId}
          onChange={handleInputChange}
          required
        >
          <option value="">Select a Train</option>
          {trainData.map((train) => (
            <option key={train.trainId} value={train.trainId}>
              {train.id.slice(-4)} - {train.trainName}
            </option>
          ))}
        </select>
        <br /><br />

        <label htmlFor="fromLocation">From Location:</label>
        <input
          type="text"
          id="fromLocation"
          name="fromLocation"
          value={scheduleData.fromLocation}
          onChange={handleInputChange}
          required
        />
        <br /><br />

        <label htmlFor="toLocation">To Location:</label>
        <input
          type="text"
          id="toLocation"
          name="toLocation"
          value={scheduleData.toLocation}
          onChange={handleInputChange}
          required
        />
        <br /><br />

        <label htmlFor="departureTime">Departure Time:</label>
        <input
          type="time"
          id="departureTime"
          name="departureTime"
          value={scheduleData.departureTime}
          onChange={handleInputChange}
          required
        />
        <br /><br />

        <label htmlFor="arrivalTime">Arrival Time:</label>
        <input
          type="time"
          id="arrivalTime"
          name="arrivalTime"
          value={scheduleData.arrivalTime}
          onChange={handleInputChange}
          required
        />
        <br /><br />

        <label htmlFor="scheduleDate">Schedule Date:</label>
        <input
          type="date"
          id="scheduleDate"
          name="scheduleDate"
          value={scheduleData.scheduleDate}
          onChange={handleInputChange}
          required
        />
        <br /><br />

        <input type="submit" value="Add Schedule" />
      </form>
    </div>
  );
}

export default AddSchedule;
