import Point from './Point.js';
import {useSelector} from "react-redux";

export default function Points(props) {
    let imgR = useSelector(state => state.imgR);
    let entries = useSelector(state => state.entries);

    let list = [];
    entries.forEach((e) => {
        list.push((
            <Point x={e.x}
                    y={e.y}
                    r={imgR}
                    h={e.hit}/>
        ));
    });
    return <g id="points">{list}</g>;
}