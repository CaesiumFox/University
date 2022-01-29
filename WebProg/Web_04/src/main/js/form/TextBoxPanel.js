export default function TextBoxPanel(props) {
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
