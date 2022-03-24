import React from 'react';
import LogoutButton from './LogoutButton';

export default function Header(props) {
    return (
        <div className="header">
            <p>{props.name}
                <span id="group_label">({props.group})</span></p>
            <p><span id="variant_label">
                Вариант: {props.variant}</span></p>
            {props.link && <LogoutButton/>}
        </div>
    );
}

Header.defaultProps = {
    name: "John Doe",
    group: "A0000",
    variant: "0",
    link: false
};
