import { React, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { authenticationState } from './atoms/AuthenticationAtom';
import { JWTState } from './atoms/TokenAtom';

export default function Logout() {
    const setAuthenticated = useRecoilState(authenticationState)[1];
    const setToken = useRecoilState(JWTState)[1];

    useEffect(() => {
        setAuthenticated(false);
        setToken(null);
        window.location.href = "/";
    });
}