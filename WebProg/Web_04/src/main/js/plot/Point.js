import React from "react";

export default function Point(props) {
    let x = props.x;
    let y = props.y;
    let r = props.r;
    let h = props.h;

    if (r === 0) {
        return (
            <circle cx={50}
                    cy={50}
                    r="0.5"
                    style={{fill: 'var(--graph-' + (h ? "hit" : 'no-hit') + ')'}}/>
        );
    }

    return (
        <circle cx={x * 40 / Math.abs(r) + 50}
                cy={50 - y * 40 / Math.abs(r)}
                r="0.5"
                style={{fill: 'var(--graph-' + (h ? "hit" : 'no-hit') + ')'}}/>
    );
}
