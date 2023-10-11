import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';

function ScheduleList() {
    const [schedules, setSchedules] = useState([]);
    const [statusDropdowns, setStatusDropdowns] = useState({});
    const [statusUpdates, setStatusUpdates] = useState({});
    const [showStatusDropdowns, setShowStatusDropdowns] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch schedules from your API here and set them in the 'schedules' state
        async function fetchSchedules() {
            try {
                const response = await fetch("/api/schedules");
                if (response.ok) {
                    const data = await response.json();
                    setSchedules(data);
                } else {
                    console.error("Failed to fetch schedules.");
                }
            } catch (error) {
                console.error("Error:", error);
            }
        }

        fetchSchedules();
    }, []);

    const handleStatusChange = async (id, newStatus) => {
        try {
            // Update the statusUpdates state with the new status
            setStatusUpdates({
                ...statusUpdates,
                [id]: newStatus,
            });

            // Hide the status dropdown after selecting a new status
            setShowStatusDropdowns({
                ...showStatusDropdowns,
                [id]: false,
            });

            // Create an updated schedule object with the new status
            const updatedSchedule = {
                ...schedules.find((schedule) => schedule.id === id),
                scheduleStatus: newStatus,
            };

            // Send a PUT request to update the schedule status in the API
            const response = await fetch(`/api/schedules/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(updatedSchedule),
            });

            if (response.ok) {
                // Schedule status updated successfully
                console.log("Schedule status updated successfully");

                // Update the 'schedules' state with the updated status
                setSchedules((prevSchedules) =>
                    prevSchedules.map((schedule) =>
                        schedule.id === id ? updatedSchedule : schedule
                    )
                );
            } else {
                // Handle the case where the update request failed
                console.error("Failed to update schedule status");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    const handleDeleteClick = async (id) => {
        // Display a window.confirm dialog for the delete confirmation
        const confirmed = window.confirm("Are you sure you want to delete this schedule?");

        if (confirmed) {
            // Send a DELETE request to remove the schedule from the API
            try {
                const response = await fetch(`/api/schedules/${id}`, {
                    method: "DELETE",
                });

                if (response.ok) {
                    // Schedule deleted successfully
                    console.log("Schedule deleted successfully");

                    // Update the 'schedules' state by removing the deleted schedule
                    setSchedules((prevSchedules) =>
                        prevSchedules.filter((schedule) => schedule.id !== id)
                    );
                } else {
                    // Handle the case where the delete request failed
                    console.error("Failed to delete schedule");
                }
            } catch (error) {
                console.error("Error:", error);
            }
        }
    };

    // Function to handle editing a schedule
    const handleEditClick = (id) => {
        // Navigate to the edit page with the schedule's ID as a URL parameter
        navigate(`/editSchedule/${id}`);
    };

    const handleStatusClick = (id) => {
        // Show the status dropdown for the schedule with the given ID
        setShowStatusDropdowns({
            ...showStatusDropdowns,
            [id]: true,
        });
    };

    // Format the date and time values
    const formatTime = (timeString) => {
        const date = new Date(timeString);
        return date.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
    };

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleDateString();
    };

    return (
        <div>
            <h1>Schedule List</h1>
            <table>
                <thead>
                    <tr>
                        <th>Schedule ID</th>
                        <th>Train ID</th>
                        <th>From Location</th>
                        <th>To Location</th>
                        <th>Departure Time</th>
                        <th>Arrival Time</th>
                        <th>Schedule Date</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {schedules.map((schedule) => (
                        <tr key={schedule.id}>
                            <td>{schedule.id.slice(-6)}</td>
                            <td>{schedule.trainId}</td>
                            <td>{schedule.fromLocation}</td>
                            <td>{schedule.toLocation}</td>
                            <td>{formatTime(schedule.departureTime)}</td>
                            <td>{formatTime(schedule.arrivalTime)}</td>
                            <td>{formatDate(schedule.scheduleDate)}</td>
                            <td>
                                {showStatusDropdowns[schedule.id] ? (
                                    <div>
                                        <select
                                            value={statusUpdates[schedule.id] || schedule.scheduleStatus}
                                            onChange={(e) => handleStatusChange(schedule.id, e.target.value)}
                                        >
                                            <option value="Not completed">Not completed</option>
                                            <option value="Completed">Completed</option>
                                        </select>
                                        <button
                                            onClick={() => handleStatusChange(schedule.id, statusUpdates[schedule.id])}
                                        >
                                            &#10003; {/* Checkmark symbol */}
                                        </button>
                                        <button onClick={() => setShowStatusDropdowns({})}>&#10005; {/* Cross symbol */}</button>
                                    </div>
                                ) : (
                                    <div onClick={() => handleStatusClick(schedule.id)}>
                                        {schedule.scheduleStatus}
                                    </div>
                                )}
                            </td>
                            <td>
                                <button onClick={() => handleEditClick(schedule.id)}>Edit</button>
                                <button onClick={() => handleDeleteClick(schedule.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ScheduleList;
