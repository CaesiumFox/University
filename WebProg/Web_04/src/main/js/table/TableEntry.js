export default function TableEntry(props) {
    return (
        <tr>
            <td>{props.x}</td>
            <td>{props.y}</td>
            <td>{props.r}</td>
            <td>{props.hit ? "Да" : "Нет"}</td>
            <td>{props.time}</td>
            <td>{props.duration}</td>
        </tr>
    );
}
