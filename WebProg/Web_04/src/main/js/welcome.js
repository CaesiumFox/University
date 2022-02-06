class Timer extends React.Component {
    render () {
        <p><span id="timer">01.01.1970 00:00:00</span></p>
    }
}

ReactDOM.render (
    (<>
        <Header name="Авдеев Степан Сергеевич"
            group="P3214"
            variant="81863"/>
        <div className="main">
            <Timer/>
            <p><a href="app.html" class="link_to_main">Главная страница</a></p>
        </div>
    </>),
    jQuery("#react_app").get()
);