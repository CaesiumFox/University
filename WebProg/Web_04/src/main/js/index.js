import App from './app/app.jsx';
import ReactDOM from 'react-dom';
import Provider from 'react-redux';

ReactDOM.render (
    (
        <Provider store={store}>
            <App/>
        </Provider>
    ),
    jQuery("#react_app").get()
);