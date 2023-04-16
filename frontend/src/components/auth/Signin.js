import { React, useState } from "react";
import { useRecoilState } from "recoil";
import { authenticationState } from "./atoms/AuthenticationAtom";
import Spinner from "../spinners/Spinner";
import { JWTState } from "./atoms/TokenAtom";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGithub } from "@fortawesome/free-brands-svg-icons";

export default function Signin() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [invalidCredentials, setInvalidCredentials] = useState(false);
    const [showSpinner, setShowSpinner] = useState(false);
    const setAuthenticated = useRecoilState(authenticationState)[1];
    const setToken = useRecoilState(JWTState)[1];

    const signInUser = async (email, password) => {
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
            default:
                break;
        }
    }

    const onSubmit = (e) => {
        e.preventDefault();
        setShowSpinner(true);

        signInUser(email, password).then((response) => {
            setShowSpinner(false);
            if (!response.ok) {
                setInvalidCredentials(true);
                throw new Error();
            }
            return response.text();
        }).then((data) => {
            setToken(data);
            setAuthenticated(true);
            window.location.href = "/user";
        }).catch(() => { });
    }

    return (
        <article>
            {!showSpinner ?
                <div>
                    <form className="auth-form" onSubmit={onSubmit}>
                        <input required name="email" value={email} onChange={onChange} type="text" placeholder="Enter your email" aria-label="Email" />
                        <input required name="password" value={password} onChange={onChange} type="password" placeholder="Enter your password" aria-label="Password" />
                        {invalidCredentials ? <span style={{ color: "red" }}>Invalid credentials</span> : null}
                        <button type="submit">SIGN IN</button>
                    </form>
                    <p className="line-text"><span className="line-text-content">OR</span></p>
                    <a className="social-signin-btn" href="http://localhost:8080/login/oauth2/code/github">
                        Sign in with Github
                        <FontAwesomeIcon style={{ marginLeft: "5px" }} icon={faGithub} />
                    </a>
                    <p style={{ fontSize: "small", marginTop: "10px" }}>Not a member yet? <Link to="/signup">Sign up</Link></p>
                </div> : <Spinner />}
        </article>
    )
}