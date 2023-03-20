import { React, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { authenticationState } from './atoms/AuthenticationAtom';

export default function Logout() {
    const setAuthenticated = useRecoilState(authenticationState)[1];

    useEffect(() => {
        setAuthenticated(false);
        localStorage.clear("token");
        window.location.href = "/";
    }, []);
}