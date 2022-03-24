import React from 'react';
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

    function sendRequest(e) {
        e.preventDefault();
        if (x.length === 0 || r.length === 0 ||
            y.length === 0 || isNaN(y) || y < -3 || y > 5)
            return;
        $.post({
            url: "/post-data",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                x: x,
                y: y,
                r: r
            })
        }).done(function(data) {
            dispatch({type: "PUSH_ENTRIES", payload: data.entries});
        }).fail(function(jqXHR, textStatus) {
            alert('Sending ajax failed: ' + textStatus);
        });
    }

    return (
        <form method="post" name="numbers" onSubmit={sendRequest}>
            <div className="form_panel" id="the_form_panel">
                <FormLabel variable="X"/>
                <XCheckBoxPanel/>
                <FormLabel variable="Y"/>
                <YTextBoxPanel/>
                <FormLabel variable="R"/>
                <RCheckBoxPanel/>
                <FormLabel variable="ImgR"/>
                <ImgRButtonPanel/>
                <SubmitButton text="Отправить"/>
            </div>
        </form>
    );
}
