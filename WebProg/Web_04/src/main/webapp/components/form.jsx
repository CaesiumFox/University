class FormLabel extends React.Component {
    render () {
        return (
            <div className="form_label">
                <span>{this.props.variable}</span>
            </div>
        );
    }
}

class CheckBoxPanel extends React.Component {
    render () {
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
            <div className="cb_panel" id={this.props.variable + '_checkbox_panel'}><div>
                {list}
            </div></div>
        );
    }
}

class TextBoxPanel extends React.Component {
    render () {
        return (
            <div className="text_field"
                 id={'text_field_' + this.props.variable}>
                <input type="text"
                       id={this.props.variable + '_text'}
                       maxLength="16"
                       name={this.props.variable}
                       value={this.props.value}/>
            </div>
        );
    }
}

class SubmitButton extends React.Component {
    render () {
        return (
            <div className="submit_button">
                <input type="submit" value={this.props.text}/>
            </div>
        );
    }
}

class Form extends React.Component {
    render () {
        return (
            <form method="post" name="numbers">
                <div className="form+panel">
                    <FormLabel variable="X"></FormLabel>
                    <CheckBoxPanel variable="x" min="-4" max="4"/>
                    <FormLabel variable="Y"></FormLabel>
                    <CheckBoxPanel variable="y" value="0"/>
                    <FormLabel variable="R"></FormLabel>
                    <CheckBoxPanel variable="r" min="-4" max="4"/>
                    <SubmitButton text="Отправить"/>
                </div>
            </form>
        );
    }
}
