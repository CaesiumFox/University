import FormLabel from './FormLabel.js';
import XCheckBoxPanel from './XCheckBoxPanel.js';
import RCheckBoxPanel from './RCheckBoxPanel.js';
import YTextBoxPanel from './YTextBoxPanel.js';
import SubmitButton from './SubmitButton.js';
import $ from 'jquery';

export default function Form(props) {
    let dispatch = useDispatch();
    let x = useSelector(state => state.x);
    let y = useSelector(state => state.y);
    let r = useSelector(state => state.r);
    let user = useSelector(state => state.user);
    let password = useSelector(state => state.password);

    function sendRequest(e) {
        e.preventDefault();
        $.post({
            url: "/post-data",
            dataType: "json",
            data: {
                user: user,
                password: password,
                type: "form",
                x: x,
                y: y,
                r: r
            }
        }).done(function(data) {
            dispatch({type: "PUSH_ENTRIES", payload: data.entries});
        }).fail(function(jqXHR, textStatus) {
            alert('Sending ajax failed: ' + textStatus);
        });
    }

    return (
        <form method="post" name="numbers" onsubmit={sendRequest}>
            <div className="form+panel">
                <FormLabel variable="X"></FormLabel>
                <XCheckBoxPanel/>
                <FormLabel variable="Y"></FormLabel>
                <YTextBoxPanel/>
                <FormLabel variable="R"></FormLabel>
                <RCheckBoxPanel/>
                <SubmitButton text="Отправить"/>
            </div>
        </form>
    );
}
