import React from 'react';
import Header from './common/Header.js';
import LoginForm from './login/LoginForm.js';

export default function Welcome(props) {
    return (<>
        <Header name="Авдеев Степан Сергеевич"
            group="P3214"
            variant="81863"/>
        <div className="main welcome_main">
            <LoginForm/>
        </div>
    </>);
}