import React from 'react';

export default function NoTableEntries(props) {
    return (
        <tr>
            <td className="no_queries" colSpan="7">
                Ещё никаких запросов не было
            </td>
        </tr>
    );
}
