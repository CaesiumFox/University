import FormLabel from './FormLabel.js';
import CheckBoxPanel from './CheckBoxPanel.js';
import TextBoxPanel from './TextBoxPanel.js';
import SubmitButton from './SubmitButton.js';

export default function Form(props) {
    return (
        <form method="post" name="numbers">
            <div className="form+panel">
                <FormLabel variable="X"></FormLabel>
                <CheckBoxPanel variable="x" min="-4" max="4"/>
                <FormLabel variable="Y"></FormLabel>
                <TextBoxPanel variable="y" value="0"/>
                <FormLabel variable="R"></FormLabel>
                <CheckBoxPanel variable="r" min="-4" max="4"/>
                <SubmitButton text="Отправить"/>
            </div>
        </form>
    );
}
