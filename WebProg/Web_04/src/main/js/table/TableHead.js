import React from 'react';

export default function TableHead(props) {
    return (
        <thead>
            <tr>
                <td className="inverse_selected">X</td>
                <td className="inverse_selected">Y</td>
                <td className="inverse_selected">R</td>
                <td className="inverse_selected">A</td>
                <td className="inverse_selected">T</td>
                <td className="inverse_selected">&Delta;T, ns</td>
                <td className="inverse_selected">U</td>
            </tr>
        </thead>
    );
}
