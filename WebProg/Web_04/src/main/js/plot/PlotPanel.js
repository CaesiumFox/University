import Curve from './Curve.js';
import Axes from './Axes.js';
import Points from './Points.js';

export default function PlotPanel(props) {
    <svg id="the_image"
            width="100%"
            height="100%"
            viewBox="0 0 100 100">
        <style>
            @import url("/svgstyle.css");
        </style>

        <Curve r={this.props.r}/>
        <Axes r={this.props.r}/>
        <Points/>
    </svg>
}
