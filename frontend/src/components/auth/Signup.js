import React from "react";
import { useState } from "react";

export default function Signup() {
    const [email, setEmail] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [password, setPassword] = useState("");
    const [passwordConfirmation, setPasswordConfirmation] = useState("");
    const [emailAlreadyTaken, setEmailAlreadyTaken] = useState(false);
    const [differentPasswords, setDifferentPasswords] = useState(false);

    const signUpUser = async(email, firstName, lastName, password) => {
        return await fetch("http://localhost:8080/api/v1/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                    "email": email,
                    "firstName": firstName,
                    "lastName": lastName,
                    "password": password
                }),
        });
    }

    const onChange = (e) => {
        switch (e.target.name) {
            case "email":
                setEmail(e.target.value);
                break;
            case "firstName":
                setFirstName(e.target.value);
                break;
            case "lastName":
                setLastName(e.target.value);
                break;
            case "password":
                setPassword(e.target.value);
                break;
            case "passwordConfirmation":
                setPasswordConfirmation(e.target.value);
                break;
        }
    }

    const onSubmit = (e) => {
        e.preventDefault();
        if (password !== passwordConfirmation) {
            setDifferentPasswords(true);
            return;
        }

        signUpUser(email, firstName, lastName, password).then((response) => {
            if (response.status === 201) {
                setEmailAlreadyTaken(false);
                window.location.href = "/signin";
            } else if (response.status === 409) {
                setEmailAlreadyTaken(true);
            }
        }).catch(() => {});
    }

    return (
        <form className="auth-form" onSubmit={onSubmit}>
            <input name="email" value={email} onChange={onChange} type="text" placeholder="Enter your email"/>
            { emailAlreadyTaken ? <span style={{color: "red"}}>Email already taken</span> : null }
            <input name="firstName" value={firstName} onChange={onChange} type="text" placeholder="Enter your first name"/>
            <input name="lastName" value={lastName} onChange={onChange} type="text" placeholder="Enter your last name"/>
            <input name="password" value={password} onChange={onChange} type="password" placeholder="Enter your password"/>
            <input name="passwordConfirmation" value={passwordConfirmation} onChange={onChange} type="password" placeholder="Confirm your password"/>
            { differentPasswords ? <span style={{color: "red"}}>Passwords are different</span> : null }
            <button type="submit">SIGN UP</button>
        </form>
    )
}