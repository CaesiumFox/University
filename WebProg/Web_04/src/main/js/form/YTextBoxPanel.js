import { useDispatch, useSelector } from "react-redux";

export default function YTextBoxPanel(props) {
    let dispatch = useDispatch();

    function onYInput(event) {
        dispatch({type: "SET_Y", payload: event.target.value});
    }

    return (
        <div className="text_field"
                id="text_field_y">
            <input type="text"
                    id="y_text"
                    maxLength="16"
                    name="y"
                    value="0"
                    onInput={
                        (event) => {
                            dispatch({type: "SET_Y", payload: event.target.value});
                        }
                    }/>
        </div>
    );
}
