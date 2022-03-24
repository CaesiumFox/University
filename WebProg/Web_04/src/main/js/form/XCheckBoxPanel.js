import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import $ from "jquery";

export default function XCheckBoxPanel(props) {
    let dispatch = useDispatch();
    let xState = useSelector(state => state.x);

    if (xState.length === 0)
        $("#the_form_panel").css("--show-x-error", "visible");
    else
        $("#the_form_panel").css("--show-x-error", "hidden");

    let list = [];
    for (let val = -4; val <= 4; val++) {
        let str = 'x' + val.toString().replace('-','m');
        list.push((
            <div>
                <input type="checkbox"
                        id={str + '_checkbox'}
                        name={str}
                        value={str}
                        checked={xState.indexOf(val) !== -1}
                        onClick={()=>{
                            dispatch({type: "SWITCH_X", payload: val});
                        }}/>
                <label htmlFor={str + '_checkbox'}>
                    {val.toString().replace('-','\u2212')}
                </label>
            </div>
        ));
    }
    return (
        <div className="cb_panel" id="x_checkbox_panel"><div>
            {list}
        </div></div>
    );
}
