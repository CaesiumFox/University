import Curve from './Curve.js';
import Axes from './Axes.js';
import Points from './Points.js';
import $ from 'jquery';
import {useDispatch, useSelector} from "react-redux";

export default function PlotPanel(props) {
    let dispatch = useDispatch();
    let r = useSelector(state => state.imgR);
    let user = useSelector(state => state.user);
    let password = useSelector(state => state.password);

    function sendImgRequest(e) {
        e.preventDefault();
        let imgObj = $("#the_image");
        let mx = e.offsetX;
        let my = e.offsetY;
        let iw = imgObj.width();
        let ih = imgObj.height();
        $.post({
            url: "/post-data",
            dataType: "json",
            data: {
                user: user,
                password: password,
                type: "plot",
                x: [((10 * mx / iw - 5) * r / 4)],
                y: ((5 - 10 * my / ih) * r / 4),
                r: [r]
            }
        }).done(function(data) {
            dispatch({type: "PUSH_ENTRIES", payload: data.entries});
        }).fail(function(jqXHR, textStatus) {
            alert('Sending ajax failed: ' + textStatus);
        });
    }

    return (
        <svg id="the_image"
                width="100%"
                height="100%"
                viewBox="0 0 100 100"
                onClick={sendImgRequest}>
            <style>
                @import url("/svgstyle.css");
            </style>

            <Curve r={this.props.r}/>
            <Axes r={this.props.r}/>
            <Points/>
        </svg>
    );
}
