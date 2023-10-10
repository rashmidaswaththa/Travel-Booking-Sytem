import React, { useState, useEffect } from 'react';

function TrainList() {
    const [trains, setTrains] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    // Function to fetch the list of trains from the server
    const fetchTrains = async () => {
        try {
            const response = await fetch('/api/trains'); // Replace with your API endpoint
            if (response.ok) {
                const data = await response.json();
                setTrains(data);
                setIsLoading(false);
            } else {
                console.error('Failed to fetch trains');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    useEffect(() => {
        fetchTrains();
    }, []); // Fetch data when the component mounts

    // Function to handle deleting a train
    const handleDeleteTrain = async (id) => {
        const confirmDelete = window.confirm('Are you sure you want to delete this train record');

        if (confirmDelete) {
            try {
                const response = await fetch(`/api/trains/${id}`, {
                    method: 'DELETE',
                });

                if (response.ok) {
                    // Remove the deleted train from the state
                    setTrains((prevTrains) => prevTrains.filter((train) => train.id !== id));
                } else {
                    console.error(`Failed to delete train with ID ${id}`);
                }
            } catch (error) {
                console.error('Error:', error);
            }
        }
    };

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>Train List</h2>
            <table>
                <thead>
                    <tr>
                        <th>Train Name</th>
                        <th>Classes</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {trains.map((train) => (
                        <tr key={train.id}>
                            <td>{train.trainName}</td>
                            <td>
                                <ul>
                                    {train.classes.map((classData) => (
                                        <li key={classData.class}>
                                            {`${classData.className}: Seats - ${classData.seats}, Price - ${classData.ticketPrice}`}
                                        </li>
                                    ))}
                                </ul>
                            </td>
                            <td>
                                <button onClick={() => handleDeleteTrain(train.id)}>Delete</button>
                                {/* Add an edit button or link here with a route to edit the train */}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default TrainList;
