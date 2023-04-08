import React from "react";
import { Link } from "react-router-dom";
import "../css/error_pages.css";

export default function NotFoundErrorPage() {
    return (
        <article className="error-article">
            <h1>404 NOT FOUND</h1>
            <p>Oops! You seem to be lost.</p>
            <p>This page does not exist.</p>
            <Link to="/user">Click here to get to home page</Link>
        </article>
    )
}