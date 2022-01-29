import App from './App.js';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import $ from 'jquery';
import { store } from './store.js';

ReactDOM.render (
    (
        <Provider store={store}>
            <App/>
        </Provider>
    ),
    $("#react_app")[0]
);