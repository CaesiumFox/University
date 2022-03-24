import React from 'react';

function zeros(x) {
    if (x < 10)
        return '0' + x.toString();
    return x.toString();
}

export default function TableEntry(props) {
    let date = new Date(props.time);
    let fmtTime = zeros(date.getDate()) + '.' +
              zeros(date.getMonth()+1) + '.' +
              date.getFullYear() + ', ' +
              zeros(date.getHours()) + ':' +
              zeros(date.getMinutes()) + ':' +
              zeros(date.getSeconds());
    console.log(fmtTime);

    return (
        <tr>
            <td>{props.x}</td>
            <td>{props.y}</td>
            <td>{props.r}</td>
            <td>{props.hit ? "Да" : "Нет"}</td>
            <td>{fmtTime}</td>
            <td>{props.duration}</td>
            <td>{props.user}</td>
        </tr>
    );
}
