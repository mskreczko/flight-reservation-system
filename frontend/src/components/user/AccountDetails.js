import { React, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Spinner from "../spinners/Spinner";
import { useRecoilState } from "recoil";
import { JWTState } from "../auth/atoms/TokenAtom";

const fetchUserDetails = (token) => {
    return fetch("http://localhost:8080/api/v1/user/details", {
        headers: {
            "Authorization": "Bearer " + token,
        }
    });
}

const deleteAccount = (token) => {
    fetch("http://localhost:8080/api/v1/user", {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + token,
        }
    }).then((resp) => {
        if (resp.status === 204) {
            window.location.href = '/user/logout';
        }
    })
}

export default function AccountDetails() {
    const [tickets, setTickets] = useState([]);
    const [email, setEmail] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const token = useRecoilState(JWTState)[0];

    useEffect(() => {
        fetchUserDetails(token).then((data) => data.json())
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
                <p>Email: {email}</p>
                <p>First name: {firstName}</p>
                <p>Last name: {lastName}</p>
            </article>
            <article className="user-edit-buttons">
                <menu>
                    <li><Link className="edit-account-btn" to="changePassword">Change password</Link></li>
                    <li><Link className="edit-account-btn" to="deleteAccount" onClick={() => deleteAccount(token)}>Delete account</Link></li>
                </menu>
            </article>
            <article className="user-tickets">
                <h3>Your tickets</h3>
                {tickets ? tickets.map((t, idx) => (
                    <section key={idx}>
                        <p>{t.flight.departureAirport.icao}<span className="arrow-icon">&#8594;</span>{t.flight.destinationAirport.icao}</p>
                        <p>{t.flight.departureDate}</p>
                        <p>{t.price} $</p>
                        <p>{t.flight.airline}</p>
                    </section>
                )) : <Spinner />}
            </article>
        </section>
    )
}