import React from "react";

export default function Signin() {
    return (
        <form className="auth-form">
            <input type="text" placeholder="Enter your email"/>
            <input type="password" placeholder="Enter your password"/>
            <button type="submit">SIGN IN</button>
        </form>
    )
}