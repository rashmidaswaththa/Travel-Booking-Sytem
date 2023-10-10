import { useEffect, useState } from "react";

const entry = {
    id: "",
    name: "",
    email: "",
    password: "",
    role: "",
};

export default function EditUser(props) {


    const [data, setData] = useState({});
    const [uid, setUid] = useState("");

    // Function to show confirmation dialog
    const showConfirmDialog = () => {
        return window.confirm('Are you sure you want to save changes?');
    };

    const updateUser = async (e) => {
        e.preventDefault();

        if (!showConfirmDialog()) {
            // If the user cancels the confirmation, do not proceed with the submission
            return;
        }

        try {
            const response = await fetch("api/user/" + uid, {
                method: "PUT",
                body: JSON.stringify(entry),
                headers: {
                    "content-type": "application/json"
                }
            });

            if (response.ok) {
                console.log('User updated successfully');
                // Redirect to the train list or perform other actions
                window.location = "/userList"; // Redirect to the train list page
            } else {
                console.log(response);
                console.error(`Failed to update train with ID ${uid}`);
            }
        } catch (error) {
            console.error('Error:', error);
        }

    };

    const newData = (e) => {
        const name_ = e.target.name;
        let v_ = e.target.value;

        entry[name_] = v_;

        console.log("The New User Is: ", entry);
    };

    useEffect(() => {
        let id_ = window.location.search;
        if (id_) {
            id_ = id_.split("=")[1];
        }

        if (id_) {
            setUid(id_);

            fetch("api/user/" + id_).then(r => r.json()).then(d => {
                console.log("User for update: ", d);
                setData(d);
                Object.assign(entry, d);
            }).catch(e => console.log("Error getting user for update: ", e));
        }

    }, []);

    return (
        <main>
            <h1>Update User</h1>

            <section>
                <div className="mt-10">
                    <label htmlFor="tn">Name</label>
                    <input type="text" name="name" id="tn" defaultValue={data.name} onChange={newData} />
                </div>

                <div className="mt-10">
                    <label htmlFor="ta">Email</label>
                    <input type="email" name="email" id="ta" defaultValue={data.email} onChange={newData} />
                </div>

                <div className="mt-10">
                    <label htmlFor="cn">Password</label>
                    <input type="password" name="password" id="cn" defaultValue={data.password} onChange={newData} />
                </div>

                <div className="mt-10">
                    <label htmlFor="tnic">Role</label>
                    <input type="text" name="role" id="tnic" defaultValue={data.role} onChange={newData} />
                </div>

                <div className="mt-30 row p20 justify-btw">
                    <div className="btn cancel" onClick={() => window.location = "/userList"}>Cancel</div>
                    <div className="btn add" onClick={updateUser}>Update</div>
                </div>

            </section>
        </main>

    );


}