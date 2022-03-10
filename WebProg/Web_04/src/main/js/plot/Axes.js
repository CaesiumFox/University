import {useSelector} from "react-redux";

export default function Axes() {
    let r = useSelector(state => state.imgR);
    let labels = '';
    if (r !== 0) {
        labels = (<>
            <text className="x_axis" x="90" y="50" dy="4">{r>0 ? 'R' : '&#x02212;R'}</text>
            <text className="x_axis" x="70" y="50" dy="4">{r>0 ? 'R/2' : '&#x02212;R/2'}</text>
            <text className="x_axis" x="30" y="50" dy="4">{r<0 ? 'R/2' : '&#x02212;R/2'}</text>
            <text className="x_axis" x="10" y="50" dy="4">{r<0 ? 'R' : '&#x02212;R'}</text>

            <text className="y_axis" x="50" y="10" dx="-2" dy="1">{r>0 ? 'R' : '&#x02212;R'}</text>
            <text className="y_axis" x="50" y="30" dx="-2" dy="1">{r>0 ? 'R/2' : '&#x02212;R/2'}</text>
            <text className="y_axis" x="50" y="70" dx="-2" dy="1">{r<0 ? 'R/2' : '&#x02212;R/2'}</text>
            <text className="y_axis" x="50" y="90" dx="-2" dy="1">{r<0 ? 'R' : '&#x02212;R'}</text>
        </>);
    }
    return (
        <g id="axes">
            <path className="axis" d="M 50 95 V 5 m -1 2 l 1 -2 l 1 2"/>
            <path className="axis" d="M 5 50 H 95 m -2 -1 l 2 1 l -2 1"/>

            <path className="axis" d="M 50 10 m -1 0 l 2 0"/>
            <path className="axis" d="M 50 30 m -1 0 l 2 0"/>
            <path className="axis" d="M 50 70 m -1 0 l 2 0"/>
            <path className="axis" d="M 50 90 m -1 0 l 2 0"/>

            <path className="axis" d="M 10 50 m 0 -1 l 0 2"/>
            <path className="axis" d="M 30 50 m 0 -1 l 0 2"/>
            <path className="axis" d="M 70 50 m 0 -1 l 0 2"/>
            <path className="axis" d="M 90 50 m 0 -1 l 0 2"/>

            {labels}
            <text className="y_axis" x="50" y="50" dx="-2" dy="4">O</text>
        </g>
    );
}
