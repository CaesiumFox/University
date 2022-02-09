import App from './App.js';
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