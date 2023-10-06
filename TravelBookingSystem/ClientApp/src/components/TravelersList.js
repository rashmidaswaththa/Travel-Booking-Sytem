import { useEffect, useState } from "react";
import React, { Component } from "react";


export default function Home() {

    const [students, setStudents] = useState([]);

    useEffect(() => {
        fetch("api/traveler").then(r => r.json()).then(d => {
            console.log("The travelers are: ", d);
            setStudents(d);
        }).catch(e => console.log("The error fetching all travelers: ", e));
    }, []);

    return (
        <main>
            <h1>Ticket Reservation Application</h1>

            <div className="add-btn">
                <a href="/addTraveler">Add New Traveler</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Contact Number</th>
                        <th>NIC</th>
                        <th>Active Status</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        students.length === 0 ? <tr className="row waiting"><td className="row">Loading<span className="loading">...</span></td></tr> :
                            students.map(traveler => <tr key={traveler.id}>
                                <td>{traveler.name}</td>
                                <td>{traveler.address}</td>
                                <td>{traveler.contactNo}</td>
                                <td>{traveler.nic}</td>
                                <td>{traveler.activeStatus}</td>
                                <td><a href={"/editTraveler?id=" + traveler.id}>Edit</a></td>
                                <td>Delete</td>
                            </tr>)
                    }
                </tbody>
            </table>
        </main>



    );


}