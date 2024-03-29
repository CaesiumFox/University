import React from 'react';
import { useDispatch, useSelector } from 'react-redux';

export default function ImgRButtonPanel(props) {
    let dispatch = useDispatch();
    let r = useSelector(state => state.imgR);

    let list = [];
    for (let val = -4; val <= 4; val++) {
        let str = 'r' + val.toString().replace('-','m');
        list.push((
            <div>
                <input type="button"
                       id={str + '_button'}
                       name={str}
                       value={val.toString().replace('-','\u2212')}
                       style={{
                           borderWidth: "2px",
                           borderStyle: "solid",
                           borderColor: ((r == val) ? "var(--accent-color)" : "transparent"),
                           backgroundColor: ((r == val) ?
                               "var(--half-accent-color)" : "")
                       }}
                       onClick={()=>{
                           dispatch({type: "SET_IMG_R", payload: val});
                       }}/>
            </div>
        ));
    }
    return (
        <div className="btn_panel" id="img_r_button_panel"><div>
            {list}
        </div></div>
    );
}