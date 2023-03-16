import React from "react";
import "../css/main.css";

export default function FlightSearch() {
    return (
        <div className="search-container">
            <form className="search-form">
                <input type="text" placeholder="From"/>
                <input type="text" placeholder="To"/>
                <input type="date" placeholder="Depart"/>
                <input type="date" placeholder="Return"/>
                <button type="submit">SEARCH</button>
            </form>
            <article className="search-results">
                <h3>Search results</h3>
                <section className="single-search-result">
                    <span>JFK<span className="arrow-icon">&#8594;</span>WAW</span>
                    <p>Airline: LOT</p>
                    <p>Price: 2500.0$</p>
                    <p>Departure date: 03/04/2023</p>
                    <div className="book-flight-container">
                        <a className="book-flight-btn" href="book_flight">BUY</a>
                    </div>
                </section>
                <section className="single-search-result">
                    <span>EDDF<span className="arrow-icon">&#8594;</span>LFPG</span>
                    <p>Airline: Lufthansa</p>
                    <p>Price: 2550.0$</p>
                    <p>Departure date: 13/12/2023</p>
                    <div className="book-flight-container">
                        <a className="book-flight-btn" href="book_flight">BUY</a>
                    </div>
                </section>
            </article>
        </div>
    )
}