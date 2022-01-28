export class App extends React.Component {
    render () {
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
}