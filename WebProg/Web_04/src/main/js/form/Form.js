import FormLabel from './FormLabel.js';
import XCheckBoxPanel from './XCheckBoxPanel.js';
import RCheckBoxPanel from './RCheckBoxPanel.js';
import YTextBoxPanel from './YTextBoxPanel.js';
import SubmitButton from './SubmitButton.js';
import $ from 'jquery';

export default function Form(props) {
    function sendRequest(e, x, y, r) {
        e.preventDefault();

    }

    return (
        <form method="post" name="numbers" >
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
