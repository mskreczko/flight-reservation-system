import { React, useState, useEffect } from "react";
import Pagination from "react-bootstrap/Pagination";
import { Link } from "react-router-dom";
import Spinner from "../spinners/Spinner";
import "../css/main.css";

export default function FlightSearch() {
    const [pageNumber, setPageNumber] = useState(0);
    const [pageInfo, setPageInfo] = useState({});
    const [flights, setFlights] = useState([]);
    const [fromIcao, setFromIcao] = useState("");
    const [toIcao, setToIcao] = useState("");
    const [departDate, setDepartDate] = useState("");
    const [returnDate, setReturnDate] = useState("");
    const [showSpinner, setShowSpinner] = useState(false);

    const fetchFlights = (departure, destination, departDate, returnDate, pageNumber) => {
        return fetch(
            "http://localhost:8080/api/v1/unauthenticated/flights/find?departure_icao=" + departure +
            "&destination_icao=" + destination + "&departure_date=" + departDate + "&return_date=" + returnDate + "&pageNumber=" + pageNumber);
    }

    const fetchFlightsOnLoad = (pageNumber) => {
        return fetch("http://localhost:8080/api/v1/unauthenticated/flights?pageNumber=" + pageNumber);
    }

    const onSubmit = (e) => {
        e.preventDefault();
        setShowSpinner(true);

        fetchFlights(fromIcao, toIcao, departDate, returnDate, pageNumber).then((data) => data.json())
            .then((body) => {
                setShowSpinner(false);
                setFlights(body.content);
                setPageInfo({ totalItems: body.totalElements, itemsPerPage: body.pageSize, totalPages: body.totalPages, currentPage: body.pageNumber + 1 });
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
            default:
                break;
        }
    }

    const nextPage = (n) => {
        setPageNumber(n + 1);
    }

    const previousPage = (n) => {
        setPageNumber(n - 1);
    }

    useEffect(() => {
        setShowSpinner(true);
        fetchFlightsOnLoad(pageNumber).then((resp) => resp.json())
            .then((body) => { setShowSpinner(false); setFlights(body.content); setPageInfo({ totalItems: body.totalElements, itemsPerPage: body.pageSize, totalPages: body.totalPages, currentPage: body.pageNumber + 1 }) });
    }, [pageNumber]);

    let items = [];
    for (let n = 1; n <= pageInfo.totalPages; n++) {
        items.push(
            <Pagination.Item key={n} active={n === pageInfo.currentPage} onClick={() => setPageNumber(n - 1)}>
                {n}
            </Pagination.Item>
        );
    }

    return (
        <section className="search-container">
            <form className="search-form" onSubmit={onSubmit}>
                <input required name="from_icao" value={fromIcao} onChange={onChange} type="text" placeholder="From" />
                <input required name="to_icao" value={toIcao} onChange={onChange} type="text" placeholder="To" />
                <input name="depart_date" value={departDate} onChange={onChange} type="date" placeholder="Depart" />
                <input name="return_date" value={returnDate} onChange={onChange} type="date" placeholder="Return" />
                <button type="submit">SEARCH</button>
            </form>
            <article className="search-results">
                <h3>Search results</h3>
                {!showSpinner ? flights.map((f, idx) => (
                    <section key={idx} className="single-search-result">
                        <span>{f.departureAirport.icao}<span className="arrow-icon">&#8594;</span>{f.destinationAirport.icao}</span>
                        <p>Airline: {f.airline}</p>
                        <p>Departure date: {f.departureDate}</p>
                        <div className="book-flight-container">
                            <Link className="book-flight-btn" to="/user/book-flight" state={{ f }}>BUY</Link>
                        </div>
                    </section>
                )) : <Spinner />}
            </article>
            <Pagination>
                <Pagination.Item onClick={() => previousPage(pageNumber)}>Previous</Pagination.Item>
                {items}
                <Pagination.Item onClick={() => nextPage(pageNumber)}>Next</Pagination.Item>
            </Pagination>
        </section>
    )
}