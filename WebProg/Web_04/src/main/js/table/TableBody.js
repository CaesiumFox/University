import React from 'react';
import TableEntry from './TableEntry.js';
import NoTableEntries from './NoTableEntries.js';
import { useSelector } from 'react-redux';

export default function TableBody(props) {
    let entries = useSelector(state => state.entries);

    if (entries.length === 0) {
        return <tbody><NoTableEntries/></tbody>
    }

    let list = [];
    entries.forEach((e) => {
        list.push((
            <TableEntry x={e.x}
                        y={e.y}
                        r={e.r}
                        hit={e.hit}
                        time={e.time}
                        duration={e.duration}
                        user={e.user}/>
        ));
    });
    return <tbody>{list}</tbody>;
}
