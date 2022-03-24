import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { store } from './store.js';
import $ from "jquery";
import App from './App.js';
import Welcome from './Welcome.js';


if ($('#react').length) {
    $.get({
        url: "/current-data",
        dataType: "json",
    }).done(function(data) {
        store.dispatch({type: "SET_ENTRIES", payload: data.entries});
        store.dispatch({type: "SET_USERNAME", payload: data.currentUsername});
    }).fail(function(jqXHR, textStatus) {
        alert('Sending ajax failed: ' + textStatus);
    });

    ReactDOM.render(
        <Provider store={store}>
            <App/>
        </Provider>,
        $('#react')[0]
    );  
}

if ($('#react_welcome').length) {
    ReactDOM.render(
        <Welcome/>,
        $('#react_welcome')[0]
    );
}
