import { React, useState } from "react";
import { Outlet, Link } from "react-router-dom";
import "../css/main.css";

export default function Main() {
    return (
        <div className="wrapper">
            <header className="desktop-header">
                <a className="header-text" href="/"><h1>Flight Booking</h1></a>
                <nav>
                    <menu>
                        <li><Link className="nav-link" to="/about">ABOUT</Link></li>
                        <li><Link className="nav-link" to="/contact">CONTACT</Link></li>
                    </menu>
                </nav>
                <div>
                    <Link className="nav-link-auth" to="/signin">SIGN IN</Link>
                    <Link className="nav-link-auth" to="/signup">SIGN UP</Link>
                </div>
            </header>
            <header className="responsive-header">
                <a className="header-text" href="/"><h1>Flight Booking</h1></a>
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
