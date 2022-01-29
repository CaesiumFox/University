import Header from './Header.js';
import Form from './Form.js';
import PlotPanel from './PlotPanel';
import Table from './Table.js';

export default function App(props) {
    return (<>
        <Header name="Авдеев Степан Сергеевич"
            group="P3214"
            variant="81863"
            link/>
        <div className="main">
            <div className="top_panel">
                <Form/>
                <PlotPanel/>
            </div>
            <Table/>
        </div>
    </>);
}