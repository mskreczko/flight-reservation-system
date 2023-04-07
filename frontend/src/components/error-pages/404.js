import React from "react";
import { Link } from "react-router-dom";
import "../css/error_pages.css";

export default function NotFoundErrorPage() {
    return (
        <article className="not-found-error-article">
            <h1>404 NOT FOUND</h1>
            <p>Oops! You seem to be lost.</p>
            <p>This page does not exist.</p>
            <Link to="/">Click here to get to home page</Link>
        </article>
    )
}