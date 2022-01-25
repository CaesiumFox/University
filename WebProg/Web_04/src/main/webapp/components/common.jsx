class Header extends React.Component {
    render () {
        return (
            <div className="header">
                <p>{this.props.name}
                    <span id="group_label">({this.props.group})</span></p>
                <p><span id="variant_label">
                    Вариант: {this.props.variant}</span></p>
                {this.props.link && <p><a href="welcome.jsp" className="link_to_welcome">
                    Вернуться на стартовую страницу</a></p>}
            </div>
        );
    }
}

Header.defaultProps = {
    name: "John Doe",
    group: "A0000",
    variant: "0",
    link: false
};
