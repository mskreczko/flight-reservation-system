import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Select from "react-select";
import Spinner from "../spinners/Spinner";
import "../css/main.css";

export default function BookFlight() {
    const [tickets, setTickets] = useState([]);
    const [choosenTicket, setChoosenTicket] = useState("");
    const [showSpinner, setShowSpinner] = useState(false);
    const options = [
        { value: "paypal", label: <div><img src="https://img.freepik.com/darmowe-ikony/paypal_318-674245.jpg" alt="paypal logo" height="20px" width="40px" /></div> },
        { value: "googlepay", label: <div><img src="https://cdn-icons-png.flaticon.com/512/6124/6124998.png" alt="googlepay logo" height="20px" width="40px" /></div> },
        { value: "applepay", label: <div><img src="https://seeklogo.com/images/A/apple-pay-logo-F68C9AC252-seeklogo.com.png" alt="applepay logo" height="20px" width="40px" /></div> },
        { value: "visa", label: <div><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Visa_Inc._logo.svg/1200px-Visa_Inc._logo.svg.png" alt="visa logo" height="20px" width="40px" /></div> },
        { value: "mastercard", label: <div><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/Mastercard-logo.svg/989px-Mastercard-logo.svg.png" alt="mastercard logo" height="20px" width="40px" /></div> },
    ];

    const location = useLocation();
    const flightData = location.state.f;

    const fetchTicketsForFlight = (flightId) => {
        return fetch("http://localhost:8080/api/v1/user/tickets/" + flightId, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"),
            }
        });
    }

    const handleFormSubmit = (e) => {
        e.preventDefault();
        setShowSpinner(true);
        fetch("http://localhost:8080/api/v1/user/tickets/purchase", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"),
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ "ticketId": choosenTicket }),
        }).then((resp) => {
            setShowSpinner(false);
            if (resp.ok) {
                window.location.href = "/user/success";
            }
        })
    }

    const onChange = (e) => {
        setChoosenTicket(e.target.value);
    }

    useEffect(() => {
        fetchTicketsForFlight(flightData.id).then((resp) => resp.json()).then((body) => setTickets(body));
    }, []);

    return (
        <div className="book-flight-form-wrapper">
            {!showSpinner ? <section className="book-flight-section">
                <h3>Choose your ticket</h3>
                <article className="book-flight-section-details">
                    <h4>Details</h4>
                    <span>{flightData.departureAirport.icao}<span className="arrow-icon">&#8594;</span>{flightData.destinationAirport.icao}</span>
                    <p>Airline: {flightData.airline}</p>
                    <p>Departure date: {flightData.departureDate}</p>
                </article>
                <form className="book-flight-form" onSubmit={handleFormSubmit}>
                    <article className="available-tickets">
                        {tickets ? tickets.map((t, idx) => (
                            <div className="ticket-box" key={idx}>
                                <input disabled={t.numberOfAvailableTickets === 0 ? true : false} className="ticket-box-check" type="radio" name={t.id} value={t.id} onChange={onChange} />
                                <label className="ticket-box-label" htmlFor={t.id}>
                                    <p>{t.price} $ | {t.travelClass}</p>
                                </label>
                            </div>
                        )) : <Spinner />}
                    </article>
                    <Select aria-label="Select payment method" placeholder="Select payment method" name="payment-method" className="payment-method-select" options={options} />
                    <button type="submit">BOOK FLIGHT</button>
                </form>
            </section> : <Spinner />}
        </div>
    )
}