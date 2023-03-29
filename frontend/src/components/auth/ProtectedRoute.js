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
                        <li><Link className="nav-link" to="/user/about">ABOUT</Link></li>
                        <li><Link className="nav-link" to="/user/contact">CONTACT</Link></li>
                    </menu>
                </nav>
                <div>
                    <Link className="nav-link-auth" to="/user/account">ACCOUNT</Link>
                    <Link className="nav-link-auth" to="/user/logout">LOGOUT</Link>
                </div>
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