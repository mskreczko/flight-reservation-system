import React from "react";
import { ClipLoader } from "react-spinners";

export default function Spinner() {
    return (
        <ClipLoader
            aria-label="Loading Spinner"
            data-testid="loader"
        />
    )
}