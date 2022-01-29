import Point from './Point.js';

export default function Points(props) {
    // TODO
    let list = [];
    this.state.entries.forEach((e) => {
        list.push((
            <Point x={e.x}
                    y={e.y}
                    r={state.imgR}
                    h={e.hit}/>
        ));
    });
    return <g id="points">{list}</g>;
}