import TableEntry from './TableEntry.js';
import NoTableEntries from './NoTableEntries.js';

export default function TableBody(props) {
    // TODO
    if (state.entries.length == 0) {
        return <tbody><NoTableEntries/></tbody>
    }

    let list = [];
    state.entries.forEach((e) => {
        list.push((
            <TableEntry x={e.x}
                        y={e.y}
                        r={e.r}
                        hit={e.hit}
                        time={e.timeMsg}
                        duration={e.durationMsg}/>
        ));
    });
    return <tbody>{list}</tbody>;
}
