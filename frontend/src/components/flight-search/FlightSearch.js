import { React, useState } from "react";
import "../css/main.css";

export default function FlightSearch() {
    const [flights, setFlights] = useState([]);
    const [fromIcao, setFromIcao] = useState("");
    const [toIcao, setToIcao] = useState("");
    const [departDate, setDepartDate] = useState("");
    const [returnDate, setReturnDate] = useState("");

    const fetchFlights = async (departure, destination, departDate, returnDate) => {
        return await fetch(
            "http://localhost:8080/api/v1/unauthenticated/flights/find?departure_icao=" + departure +
            "&destination_icao=" + destination + "&departure_date=" + departDate + "&return_date=" + returnDate);
    }

    const onSubmit = (e) => {
        e.preventDefault();

        fetchFlights(fromIcao, toIcao, departDate, returnDate).then((data) => data.json())
        .then((body) => { 
            setFlights(body);
        })
    }

    const onChange = (e) => {
        switch (e.target.name) {
            case "from_icao":
                setFromIcao(e.target.value);
                break;
            case "to_icao":
                setToIcao(e.target.value);
                break;
            case "depart_date":
                setDepartDate(e.target.value);
                break;
            case "return_date":
                setReturnDate(e.target.value);
                break;
        }
    }

    return (
        <div className="search-container">
            <form className="search-form" onSubmit={onSubmit}>
                <input name="from_icao" value={fromIcao} onChange={onChange} type="text" placeholder="From"/>
                <input name="to_icao" value={toIcao} onChange={onChange} type="text" placeholder="To"/>
                <input name="depart_date" value={departDate} onChange={onChange} type="date" placeholder="Depart"/>
                <input name="return_date" value={returnDate} onChange={onChange} type="date" placeholder="Return"/>
                <button type="submit">SEARCH</button>
            </form>
            <article className="search-results">
                <h3>Search results</h3>
                { flights ? flights.map((f, idx) => (
                    <section key={idx} className="single-search-result">
                        <span>{ f.departureAirport.icao }<span className="arrow-icon">&#8594;</span>{ f.destinationAirport.icao }</span>
                        <p>Airline: { f.airline }</p>
                        <p>Departure date: { f.departureDate }</p>
                        <div className="book-flight-container">
                            <a className="book-flight-btn" href="book_flight">BUY</a>
                        </div>
                    </section>
                )) : null }
            </article>
        </div>
    )
}