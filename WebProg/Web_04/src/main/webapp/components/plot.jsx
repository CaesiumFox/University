class Curve extends React.Component {
    render () {
        if (this.props.r > 0) {
            return (
                <path id="the_area"
                    d={"M 50 10 " +
                        "a 40 40 0 0 1 40 40 " +
                        "v 40 " +
                        "h -40 " +
                        "v -40 " +
                        "h -20 " +
                        "l 20 -20 " +
                        "z"}/>
            );
        }
        return (
            <path id="the_area"
                d={"M 50 90 " +
                "a 40 40 0 0 1 -40 -40 " +
                "v -40 " +
                "h 40 " +
                "v 40 " +
                "h 20 " +
                "l -20 20 " +
                "z"}/>
        );
    }
}

class Axes extends React.Component {
    render () {
        let r = this.props.r;
        let labels = '';
        if (r != 0) {
            labels = (<>
                <text className="x_axis" x="90" y="50" dy="4">{r>0 ? 'R' : '&#x02212;R'}</text>
                <text className="x_axis" x="70" y="50" dy="4">{r>0 ? 'R/2' : '&#x02212;R/2'}</text>
                <text className="x_axis" x="30" y="50" dy="4">{r<0 ? 'R/2' : '&#x02212;R/2'}</text>
                <text className="x_axis" x="10" y="50" dy="4">{r<0 ? 'R' : '&#x02212;R'}</text>

                <text className="y_axis" x="50" y="10" dx="-2" dy="1">{r>0 ? 'R' : '&#x02212;R'}</text>
                <text className="y_axis" x="50" y="30" dx="-2" dy="1">{r>0 ? 'R/2' : '&#x02212;R/2'}</text>
                <text className="y_axis" x="50" y="70" dx="-2" dy="1">{r<0 ? 'R/2' : '&#x02212;R/2'}</text>
                <text className="y_axis" x="50" y="90" dx="-2" dy="1">{r<0 ? 'R' : '&#x02212;R'}</text>
            </>);
        }
        return (
            <g id="axes">
                <path className="axis" d="M 50 95 V 5 m -1 2 l 1 -2 l 1 2"></path>
                <path className="axis" d="M 5 50 H 95 m -2 -1 l 2 1 l -2 1"></path>

                <path className="axis" d="M 50 10 m -1 0 l 2 0"></path>
                <path className="axis" d="M 50 30 m -1 0 l 2 0"></path>
                <path className="axis" d="M 50 70 m -1 0 l 2 0"></path>
                <path className="axis" d="M 50 90 m -1 0 l 2 0"></path>

                <path className="axis" d="M 10 50 m 0 -1 l 0 2"></path>
                <path className="axis" d="M 30 50 m 0 -1 l 0 2"></path>
                <path className="axis" d="M 70 50 m 0 -1 l 0 2"></path>
                <path className="axis" d="M 90 50 m 0 -1 l 0 2"></path>

                {labels}
                <text className="y_axis" x="50" y="50" dx="-2" dy="4">O</text>
            </g>
        );
    }
}

class Point extends React.Component {
    render () {
        let x = this.props.x;
        let y = this.props.y;
        let r = this.props.r;
        let h = this.props.h;
        return (
            <circle cx={x * 40 / r + 50}
                    cy={50 - y * 40 / r}
                    r="0.5"
                    style={'fill: var(--graph-' + (h ? "hit" : 'no-hit') + ')'}/>
        );
    }
}

class Points extends React.Component {
    render () {
        // TODO
        return <g id="points"></g>;
    }
}

class PlotPanel extends React.Component {
    render () {
        <svg id="the_image"
             width="100%"
             height="100%"
             viewBox="0 0 100 100"
        >
            <style>
                @import url("./styles/svgstyle.css");
            </style>

            <Curve r={this.props.r}/>
            <Axes r={this.props.r}/>
            <Points/>
        </svg>
    }
}