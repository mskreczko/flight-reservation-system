import React from "react";

export default function Signup() {
    return (
        <form className="auth-form">
            <input type="text" placeholder="Enter your email"/>
            <input type="text" placeholder="Enter your first name"/>
            <input type="text" placeholder="Enter your last name"/>
            <input type="password" placeholder="Enter your password"/>
            <input type="password" placeholder="Confirm your password"/>
            <button type="submit">SIGN UP</button>
        </form>
    )
}