import { React } from "react";
import { Outlet, Link } from "react-router-dom";
import "../css/main.css";

export default function Main() {
    return (
        <div className="wrapper">
            <header className="desktop-header">
                <a className="header-text" href="/"><h1>Flight Booking</h1></a>
                <nav>
                    <menu>
                        <li><Link className="nav-link" to="/signin">SIGN IN</Link></li>
                        <li><Link className="nav-link" to="/signup">SIGN UP</Link></li>
                    </menu>
                </nav>
            </header>
            <header className="responsive-header">
                <a className="header-text" href="/"><h1>Flight Booking</h1></a>
                <nav>
                    <menu>
                        <li><Link className="nav-link" to="/signin">SIGN IN</Link></li>
                        <li><Link className="nav-link" to="/signup">SIGN UP</Link></li>
                    </menu>
                </nav>
            </header>
            <main>
                <Outlet />
            </main>
            <footer>
                <p>Michał Skreczko &copy; 2023</p>
                <Link className="footer-link" to="/about">ABOUT</Link>
                <Link className="footer-link" to="/contact">CONTACT</Link>
            </footer>
        </div>
    )
}
