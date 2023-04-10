import React from "react";
import { Link } from "react-router-dom";

export default function InternalServerErrorPage() {
    return (
        <article className="not-found-error-article">
            <h1>500 INTERNAL SERVER ERROR</h1>
            <p>Oops! It looks like something is wrong on our side.</p>
            <Link to="/user">Click here to get to home page</Link>
        </article>
    )
}