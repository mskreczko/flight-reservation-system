import { React, useState, useEffect } from "react";
import { Link } from "react-router-dom";

const fetchUserDetails = async () => {
    return await fetch("http://localhost:8080/api/v1/user/details", {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token"),
        }
    });
}

export default function AccountDetails() {
    const [tickets, setTickets] = useState([]);
    const [email, setEmail] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");

    useEffect(() => {
        fetchUserDetails().then((data) => data.json())
        .then((body) => {
            setTickets(body.tickets);
            setEmail(body.email);
            setFirstName(body.firstName);
            setLastName(body.lastName);
        })
    }, []);

    return (
        <section>
            <article className="user-details">
                <h3>User Details</h3>
                <p>Email: { email }</p>
                <p>First name: { firstName }</p>
                <p>Last name: { lastName }</p>
            </article>
            <article className="user-edit-buttons">
                <menu>
                    <li><Link to="changePassword">Change password</Link></li>
                    <li><Link to="deletePassword">Delete account</Link></li>
                </menu>
            </article>
            <article className="user-tickets">
                <h3>Your tickets</h3>
                { tickets ? tickets.map((t, idx) => (
                    <section key={idx}>
                        <span>{ t.price }</span>
                    </section>
                )) : null }
            </article>
        </section>
    )
}