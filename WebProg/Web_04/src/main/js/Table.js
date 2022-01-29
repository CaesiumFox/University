function TableHead(props) {
    return (
        <thead>
            <tr>
                <td class="inverse_selected">X</td>
                <td class="inverse_selected">Y</td>
                <td class="inverse_selected">R</td>
                <td class="inverse_selected">A</td>
                <td class="inverse_selected">T</td>
                <td class="inverse_selected">&Delta;T, ns</td>
                <td class="inverse_selected">U</td>
            </tr>
        </thead>
    );
}

function TableEntry(props) {
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

function NoTableEntries(props) {
    return (
        <tr>
            <td class="no_queries" colspan="7">
                Ещё никаких запросов не было
            </td>
        </tr>
    );
}

function TableBody(props) {
    if (this.state.entries.length == 0) {
        return <tbody><NoTableEntries/></tbody>
    }

    let list = [];
    this.state.entries.forEach((e) => {
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

function TableTip(props) {
    return (
        <p>
            X, Y, R&nbsp;&mdash; выбранные числа;<br/>
            A&nbsp;&mdash; попадание;<br/>
            T&nbsp;&mdash; текущее время;<br/>
            &Delta;T&nbsp;&mdash; время работы сервера от начала работы до получения этого результата.<br/>
            U&nbsp;&mdash; пользователь, оставивший запись.
        </p>
    );
}

export default function Table(props) {
    return (
        <div className="table_panel">
            <table id="result_table">
                <TableHead/>
                <TableBody/>
            </table>
            <TableTip/>
        </div>
    );
}