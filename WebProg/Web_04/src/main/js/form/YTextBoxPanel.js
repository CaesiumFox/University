import React from "react";
import { useDispatch, useSelector } from "react-redux";
import $ from "jquery";

export default function YTextBoxPanel(props) {
    let dispatch = useDispatch();
    let currentY = useSelector(state => state.y);

    if (currentY.length === 0 || isNaN(currentY) || currentY < -3 || currentY > 5) {
        $("#text_field_y").css("color", "var(--text-wrong-color)");
        $("#the_form_panel").css("--show-y-error", "visible");
    }
    else {
        $("#text_field_y").css("color", "var(--text-color)");
        $("#the_form_panel").css("--show-y-error", "hidden");
    }

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
                    value={currentY}
                    onInput={onYInput}/>
        </div>
    );
}
