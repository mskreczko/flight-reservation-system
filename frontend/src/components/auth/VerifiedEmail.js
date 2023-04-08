import { React, useState, useEffect } from "react";
import { useParams, useSearchParams } from "react-router-dom";

export default function VerifiedEmail() {
    const [emailVerified, setEmailVerified] = useState(false);
    const [searchParams, setSearchParams] = useSearchParams();

    useEffect(() => {
        fetch("http://localhost:8080/api/v1/auth/verifyAccount?token=" + searchParams.get("token"), {
            method: "PATCH"
        }).then((resp) => {
            if (resp.ok) {
                setEmailVerified(true);
            }
        })
    }, []);

    return (
        <article>
        { emailVerified ? <section>
            <p>Your email has been verified.</p>
            <p>You can now sign in.</p>
        </section> : null }
        </article>
    )
}