import React from "react";

export default function SubmitButton(props) {
    return (
        <div className="submit_button">
            <input type="submit" value={props.text}/>
        </div>
    );
}
