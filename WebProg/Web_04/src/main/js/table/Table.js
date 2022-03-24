import React from 'react';
import TableHead from './TableHead.js';
import TableBody from './TableBody.js';
import TableTip from './TableTip.js';

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