import React from 'react';
import Header from './common/Header.js';
import Form from './form/Form.js';
import PlotPanel from './plot/PlotPanel';
import Table from './table/Table.js';

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