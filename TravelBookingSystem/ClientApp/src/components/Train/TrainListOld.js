import React, { Component } from 'react';
import axios from 'axios'; // Make HTTP requests to your ASP.NET API

class TrainList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            trains: [], // An array to hold train data
        };
    }

    componentDidMount() {
        this.fetchTrains();
    }

    fetchTrains() {
        axios.get('api/trains') // Replace with your API endpoint to fetch all trains
            .then((response) => {
                this.setState({ trains: response.data });
            })
            .catch((error) => {
                console.error('Error fetching trains:', error);
            });
    }

    handleEdit = (trainId) => {
        // Handle the edit action, e.g., navigate to an edit page
    }

    handleDelete = (trainId) => {
        axios.delete(`api/trains/${trainId}`) // Replace with your API endpoint to delete a train
            .then(() => {
                this.fetchTrains(); // Refresh the train list after deletion
            })
            .catch((error) => {
                console.error('Error deleting train:', error);
            });
    }

    render() {
        const { trains } = this.state;

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
                            <tr key={train._id}>
                                <td>{train.trainName}</td>
                                <td>
                                    <ul>
                                        {train.schedule.map((classData) => (
                                            <li key={classData.class}>
                                                {`${classData.class}: Seats - ${classData.seatsAvailable}, Price - ${classData.ticketPrice}`}
                                            </li>
                                        ))}
                                    </ul>
                                </td>
                                <td>
                                    <button onClick={() => this.handleEdit(train._id)}>Edit</button>
                                    <button onClick={() => this.handleDelete(train._id)}>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default TrainList;
