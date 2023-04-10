import { React } from "react";
import { useRecoilState } from "recoil";
import { authenticationState } from "./atoms/AuthenticationAtom";
import { Link, Outlet } from "react-router-dom";
import "../css/main.css";

export default function ProtectedRoute() {
    const authenticated = useRecoilState(authenticationState)[0];

    if (!authenticated) {
        window.location.href = '/signin';
    }

    return (
        <div className="wrapper">
            <header className="desktop-header">
                <a className="header-text" href="/user"><h1>Flight Booking</h1></a>
                <nav>
                    <menu>
                        <li><Link className="nav-link" to="/user/account">ACCOUNT</Link></li>
                        <li><Link className="nav-link" to="/user/logout">LOGOUT</Link></li>
                    </menu>
                </nav>
            </header>
            <header className="responsive-header">
                <a className="header-text" href="/user"><h1>Flight Booking</h1></a>
                <nav>
                    <menu>
                        <li><Link className="nav-link" to="/user/account">ACCOUNT</Link></li>
                        <li><Link className="nav-link" to="/user/logout">LOGOUT</Link></li>
                    </menu>
                </nav>
            </header>
            <main>
                <Outlet />
            </main>
            <footer>
                <p>Michał Skreczko &copy; 2023</p>
                <Link className="footer-link" to="/user/about">ABOUT</Link>
                <Link className="footer-link" to="/user/contact">CONTACT</Link>
            </footer>
        </div>
    )
}