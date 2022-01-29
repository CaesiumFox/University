function FormLabel(props) {
    return (
        <div className="form_label">
            <span>{props.variable}</span>
        </div>
    );
}

function CheckBoxPanel(props) {
    let list = [];
    for (let val = props.min; val <= props.max; val++) {
        let str = props.variable + val.toString().replace('-','m');
        list.push((
            <div>
                <input type="checkbox"
                        id={str + '_checkbox'}
                        name={str}
                        value={str}/>
                <label for={str + '_checkbox'}>
                    {val.toString().replace('-','\u2212')}
                </label>
            </div>
        ));
    }
    return (
        <div className="cb_panel" id={props.variable + '_checkbox_panel'}><div>
            {list}
        </div></div>
    );
}

function TextBoxPanel(props) {
    return (
        <div className="text_field"
                id={'text_field_' + props.variable}>
            <input type="text"
                    id={props.variable + '_text'}
                    maxLength="16"
                    name={props.variable}
                    value={props.value}/>
        </div>
    );
}

function SubmitButton(props) {
    return (
        <div className="submit_button">
            <input type="submit" value={props.text}/>
        </div>
    );
}

export default function Form(props) {
    return (
        <form method="post" name="numbers">
            <div className="form+panel">
                <FormLabel variable="X"></FormLabel>
                <CheckBoxPanel variable="x" min="-4" max="4"/>
                <FormLabel variable="Y"></FormLabel>
                <CheckBoxPanel variable="y" value="0"/>
                <FormLabel variable="R"></FormLabel>
                <CheckBoxPanel variable="r" min="-4" max="4"/>
                <SubmitButton text="Отправить"/>
            </div>
        </form>
    );
}
