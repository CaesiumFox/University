ReactDOM.render (
    (<>
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
    </>),
    jQuery("#react_app").get()
);