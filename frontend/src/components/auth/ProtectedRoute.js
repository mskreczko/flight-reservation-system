import { React } from "react";
import { useRecoilState } from "recoil";
import { authenticationState } from "./atoms/AuthenticationAtom";
import { Outlet } from "react-router-dom";
import "../css/main.css";

export default function ProtectedRoute() {
    const authenticated = useRecoilState(authenticationState)[0];

    if (!authenticated) {
        window.location.href = '/signin';
    }

    return (
        <div className="wrapper">
            <header>
                <a href="/user"><h1>Flight Booking</h1></a>
                <nav>
                    <menu>
                        <li><a href="/user/about">ABOUT</a></li>
                        <li><a href="/user/contact">CONTACT</a></li>
                        <li><a href="/user/logout">LOGOUT</a></li>
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