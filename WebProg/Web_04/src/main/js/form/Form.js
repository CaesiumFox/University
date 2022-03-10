import FormLabel from './FormLabel.js';
import XCheckBoxPanel from './XCheckBoxPanel.js';
import RCheckBoxPanel from './RCheckBoxPanel.js';
import YTextBoxPanel from './YTextBoxPanel.js';
import SubmitButton from './SubmitButton.js';
import $ from 'jquery';
import {useDispatch, useSelector} from "react-redux";
import ImgRButtonPanel from "./ImgRButtonPanel";

export default function Form(props) {
    let dispatch = useDispatch();
    let x = useSelector(state => state.x);
    let y = useSelector(state => state.y);
    let r = useSelector(state => state.r);
    let imgR = useSelector(state => state.imgR);
    let token = useSelector(state => state.token);

    function sendRequest(e) {
        e.preventDefault();
        $.post({
            url: "/post-data",
            dataType: "json",
            data: {
                token: token,
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
        <form method="post" name="numbers" onSubmit={sendRequest}>
            <div className="form+panel">
                <FormLabel variable="X"/>
                <XCheckBoxPanel/>
                <FormLabel variable="Y"/>
                <YTextBoxPanel/>
                <FormLabel variable="R"/>
                <RCheckBoxPanel/>
                <FormLabel variable={'R<sub>(img)</sub> = ' + imgR.toString()}/>
                <ImgRButtonPanel/>
                <SubmitButton text="Отправить"/>
            </div>
        </form>
    );
}
