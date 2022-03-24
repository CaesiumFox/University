import React from "react";
import { useSelector } from "react-redux";

export default function Curve() {
    let r = useSelector(state => state.imgR);
    if (r > 0) {
        return (
            <path id="the_area"
                d={"M 50 10 " +
                    "a 40 40 0 0 1 40 40 " +
                    "v 40 " +
                    "h -40 " +
                    "v -40 " +
                    "h -20 " +
                    "l 20 -20 " +
                    "z"}/>
        );
    }
    else if (r < 0) {
        return (
            <path id="the_area"
                d={"M 50 90 " +
                "a 40 40 0 0 1 -40 -40 " +
                "v -40 " +
                "h 40 " +
                "v 40 " +
                "h 20 " +
                "l -20 20 " +
                "z"}/>
        );
    }
    return (
        <circle id="the_area"
            cx="50"
            cy="50"
            r="0.5"/>
    );
}
