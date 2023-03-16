import React from "react";
import { Outlet } from "react-router-dom";
import "../css/main.css";

export default function Main() {
    return (
        <div className="wrapper">
        <header>
            <a href="/"><h1>Flight Booking</h1></a>
            <nav>
                <menu>
                    <li><a href="/signin">SIGN IN</a></li>
                    <li><a href="/signup">SIGN UP</a></li>
                    <li><a href="/about">ABOUT</a></li>
                    <li><a href="/contact">CONTACT</a></li>
                </menu>
            </nav>
        </header>
        <main>
            <Outlet/>
        </main>
        <footer>
            <p>Michał Skreczko &copy; 2023</p>
        </footer>
        </div>
    )
}
