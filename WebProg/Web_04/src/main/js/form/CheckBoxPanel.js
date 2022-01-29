export default function CheckBoxPanel(props) {
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
