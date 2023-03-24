import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Select from "react-select";
import "../css/main.css";

export default function BookFlight() {
    const options = [
        { value: "paypal", label: <div><img src="https://img.freepik.com/darmowe-ikony/paypal_318-674245.jpg" height="20px" width="40px"/></div> },
        { value: "googlepay", label: <div><img src="https://cdn-icons-png.flaticon.com/512/6124/6124998.png" height="20px" width="40px"/></div> },
        { value: "applepay", label: <div><img src="https://seeklogo.com/images/A/apple-pay-logo-F68C9AC252-seeklogo.com.png" height="20px" width="40px"/></div> },
        { value: "visa", label: <div><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5e/Visa_Inc._logo.svg/1200px-Visa_Inc._logo.svg.png" height="20px" width="40px"/></div> },
        { value: "mastercard", label: <div><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/2a/Mastercard-logo.svg/989px-Mastercard-logo.svg.png" height="20px" width="40px"/></div> },
    ];

    const location = useLocation();
    const flightData = location.state.f;

    const handleFormSubmit = () => {

    }

    return (
        <section className="book-flight-section">
            <article className="book-flight-section-details">
                <h4>Details</h4>
                <span>{ flightData.departureAirport.icao }<span className="arrow-icon">&#8594;</span>{ flightData.destinationAirport.icao }</span>
                <p>Airline: { flightData.airline }</p>
                <p>Departure date: { flightData.departureDate }</p>
                <p>Price: { flightData.price }</p>
            </article>
            <form className="book-flight-form" onSubmit={handleFormSubmit}>
                <Select placeholder="Select payment method" name="payment-method" className="payment-method-select" options={options}/>
                <button type="submit">BOOK FLIGHT</button>
            </form>
        </section>
    )
}