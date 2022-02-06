import { useDispatch, useSelector } from 'react-redux';

export default function RCheckBoxPanel(props) {
    let dispatch = useDispatch();
    let rState = useSelector(state => state.r);

    let list = [];
    for (let val = -4; val <= 4; val++) {
        let str = 'r' + val.toString().replace('-','m');
        list.push((
            <div>
                <input type="checkbox"
                        id={str + '_checkbox'}
                        name={str}
                        value={str}
                        checked={rState.indexOf(val) !== -1}
                        onclick={()=>{
                            dispatch({type: "SWITCH_R", payload: val});
                        }}/>
                <label htmlFor={str + '_checkbox'}>
                    {val.toString().replace('-','\u2212')}
                </label>
            </div>
        ));
    }
    return (
        <div className="cb_panel" id="r_checkbox_panel"><div>
            {list}
        </div></div>
    );
}
