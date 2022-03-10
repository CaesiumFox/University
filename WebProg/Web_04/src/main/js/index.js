import App from './App.js';
import Welcome from './Welcome.js';
import NoApp from './NoApp.js';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import $ from 'jquery';
import { store } from './store.js';


$.get({
    url: "/current-data",
    dataType: "json",
    data: {
        user: store.getState().user,
        password: store.getState().password
    }
}).done(function(data) {
    store.dispatch({type: "SET_ENTRIES", payload: data.entries});
}).fail(function(jqXHR, textStatus) {
    alert('Sending ajax failed: ' + textStatus);
});

ReactDOM.render (
    (
        <Provider store={store}>
            <App/>
        </Provider>
    ),
    $("#react_app")[0]
);

ReactDOM.render (
    (
        <Provider store={store}>
            <Welcome/>
        </Provider>
    ),
    $("#react_welcome")[0]
);

ReactDOM.render (
    (
        <Provider store={store}>
            <NoApp/>
        </Provider>
    ),
    $("#react_no_app")[0]
);