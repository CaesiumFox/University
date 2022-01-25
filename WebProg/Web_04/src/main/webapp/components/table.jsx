class TableHead extends React.Component {
    render () {
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
}

class TableEntry extends React.Component {
    render () {
        return (
            <tr>
                <td>{this.props.x}</td>
                <td>{this.props.y}</td>
                <td>{this.props.r}</td>
                <td>{this.props.hit ? "Да" : "Нет"}</td>
                <td>{this.props.time}</td>
                <td>{this.props.duration}</td>
            </tr>
        );
    }
}

class NoTableEntries extends React.Component {
    render () {
        return (
            <tr>
                <td class="no_queries" colspan="7">
                    Ещё никаких запросов не было
                </td>
            </tr>
        );
    }
}

class TableBody extends React.Component {
    render () {
        // TODO
        return <tbody></tbody>;
    }
}

class TableTip extends React.Component {
    render () {
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
}

class Table extends React.Component {
    render () {
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
}