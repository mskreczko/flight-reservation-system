import { React, useState } from "react";
import { useRecoilState } from "recoil";
import { authenticationState } from "./atoms/AuthenticationAtom";
import Spinner from "../spinners/Spinner";

export default function Signin() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [invalidCredentials, setInvalidCredentials] = useState(false);
    const [showSpinner, setShowSpinner] = useState(false);
    const setAuthenticated = useRecoilState(authenticationState)[1];

    const signInUser = async(email, password) => {
        return await fetch("http://localhost:8080/api/v1/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "email": email,
                "password": password,
            })
        });
    }

    const onChange = (e) => {
        switch (e.target.name) {
            case "email":
                setEmail(e.target.value);
                break;
            case "password":
                setPassword(e.target.value);
                break;
        }
    }

    const onSubmit = (e) => {
        e.preventDefault();
        setShowSpinner(true);
        
        signInUser(email, password).then((response) => {
            if (!response.ok) {
                setInvalidCredentials(true);
                throw new Error();
            }
            return response.text();
        }).then((data) => {
            setShowSpinner(false);
            localStorage.setItem("token", data);
            setAuthenticated(true);
            window.location.href = "/user";
        }).catch(() => {});
    }

    return (
        <article>
            { !showSpinner ? 
            <form className="auth-form" onSubmit={onSubmit}>
                <input name="email" value={email} onChange={onChange} type="text" placeholder="Enter your email"/>
                <input name="password" value={password} onChange={onChange} type="password" placeholder="Enter your password"/>
                { invalidCredentials ? <span style={{color: "red"}}>Invalid credentials</span> : null}
                <button type="submit">SIGN IN</button>
            </form> : <Spinner/> }
        </article>
    )
}