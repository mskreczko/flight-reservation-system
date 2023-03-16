import React from "react";

export default function Contact() {
    return (
        <div className="container">
            <form className="contact-form">
                <input type="text" placeholder="Enter your name"/>
                <input type="email" placeholder="Enter your email"/>
                <textarea></textarea>
                <button type="submit">SEND MESSAGE</button>
            </form>
        </div>
    )
}