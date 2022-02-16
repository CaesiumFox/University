export default function Point(props) {
    let x = props.x;
    let y = props.y;
    let r = props.r;
    let h = props.h;
    return (
        <circle cx={x * 40 / r + 50}
                cy={50 - y * 40 / r}
                r="0.5"
                style={{fill: 'var(--graph-' + (h ? "hit" : 'no-hit') + ')'}}/>
    );
}
