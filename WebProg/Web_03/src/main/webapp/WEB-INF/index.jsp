<%@ page import="io.github.caesiumfox.web2.History" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<% History history = (History) session.getAttribute("history"); %>

<!DOCTYPE html>
<html>
    <head>
        <title>Авдеев Степан P3214</title>
        <link rel="icon" href="./favicon.png">
        <link rel="stylesheet" href="./allstyles.css">
        <script type="text/javascript" src="./validation.js"></script>
    </head>
    <body onload="onbodyload();">
        <header>
            <p>
            Авдеев Степан Сергеевич <span id="group_label">(P3214)</span>
            </p>
            <p>
            <span id="variant_label">Вариант: 7446</span>
            </p>
        </header>
        <div class="main">
            <div class="top_panel">
                <form method="POST" onsubmit="return validateFinal()" name="numbers" action="${pageContext.request.contextPath}">
                    <div class="form_panel">
                        <div class="form_label with_tool_tip"><span>X</span></div>
                        <div class="x_panel" id="x_checkbox_panel">
                            <div>
                                <div><input type="radio" id="xm4_radio" name="x" value="-4"       ><label for="xm4_radio">&minus;4</label></div>
                                <div><input type="radio" id="xm3_radio" name="x" value="-3"       ><label for="xm3_radio">&minus;3</label></div>
                                <div><input type="radio" id="xm2_radio" name="x" value="-2"       ><label for="xm2_radio">&minus;2</label></div>
                                <div><input type="radio" id="xm1_radio" name="x" value="-1"       ><label for="xm1_radio">&minus;1</label></div>
                                <div><input type="radio" id="x0_radio"  name="x" value="0" checked><label for="x0_radio">0</label></div>
                                <div><input type="radio" id="x1_radio"  name="x" value="1"        ><label for="x1_radio">1</label></div>
                                <div><input type="radio" id="x2_radio"  name="x" value="2"        ><label for="x2_radio">2</label></div>
                                <div><input type="radio" id="x3_radio"  name="x" value="3"        ><label for="x3_radio">3</label></div>
                                <div><input type="radio" id="x4_radio"  name="x" value="4"        ><label for="x4_radio">4</label></div>
                            </div>
                        </div>
                        <div class="form_label"><span>Y</span></div>
                        <div class="text_field" id="text_field_y">
                            <input id="y_text" type="text" maxlength="16" name="y" value="0" oninput="validateLiveY();" onfocus="validateLiveY();" onchange="correctY();">
                        </div>
                        <div class="form_label"><span>R</span></div>
                        <div class="text_field" id="text_field_r">
                            <input id="r_text" type="text" maxlength="16" name="r" value="2" oninput="validateLiveR();" onfocus="validateLiveR();" onchange="correctR();">
                        </div>
                        <div class="submit_button"><input type="submit" value="Отправить"></div>
                    </div>
                </form>
                <div id="graph">
                    <svg id="the_image" width="100%" viewbox="0 0 100 100">
                        <defs>
                            <style>
                                #axes path {
                                    stroke: var(--graph-axes-color);
                                    stroke-width: var(--graph-axes-width);
                                    fill:none;
                                }
                                #axes text {
                                    font-family: sans-serif;
                                    font-size: 3px;
                                    fill: var(--text-color);
                                }
                                #axes text.x_axis {
                                    text-anchor: middle;
                                }
                                #axes text.y_axis {
                                    text-anchor: end;
                                }
                                #the_area {
                                    fill: var(--graph-area-color);
                                    stroke: var(--graph-area-border-color);
                                    stroke-width: var(--graph-area-border-width);
                                }
                            </style>
                        </defs>

                        <path id="the_area" d="M 50 10 l 40 40 h -20 a 20 20 0 0 1 -20 20 v -20 h -40 v -40 z"></path>

                        <g id="axes">
                            <path class="axis" d="M 50 95 V 5 m -1 2 l 1 -2 l 1 2"></path>
                            <path class="axis" d="M 5 50 H 95 m -2 -1 l 2 1 l -2 1"></path>

                            <path class="axis" d="M 50 10 m -1 0 l 2 0"></path>
                            <path class="axis" d="M 50 30 m -1 0 l 2 0"></path>
                            <path class="axis" d="M 50 70 m -1 0 l 2 0"></path>
                            <path class="axis" d="M 50 90 m -1 0 l 2 0"></path>

                            <path class="axis" d="M 10 50 m 0 -1 l 0 2"></path>
                            <path class="axis" d="M 30 50 m 0 -1 l 0 2"></path>
                            <path class="axis" d="M 70 50 m 0 -1 l 0 2"></path>
                            <path class="axis" d="M 90 50 m 0 -1 l 0 2"></path>

                            <text class="x_axis" x="90" y="50" dy="4">R</text>
                            <text class="x_axis" x="70" y="50" dy="4">R/2</text>
                            <text class="x_axis" x="30" y="50" dy="4">&minus;R/2</text>
                            <text class="x_axis" x="10" y="50" dy="4">&minus;R</text>

                            <text class="y_axis" x="50" y="10" dx="-2" dy="1">R</text>
                            <text class="y_axis" x="50" y="30" dx="-2" dy="1">R/2</text>
                            <text class="y_axis" x="50" y="70" dx="-2" dy="1">&minus;R/2</text>
                            <text class="y_axis" x="50" y="90" dx="-2" dy="1">&minus;R</text>

                            <text class="y_axis" x="50" y="50" dx="-2" dy="4">O</text>
                        </g>
                        <g id="points">
                            <%
                                if (history != null) {
                                    for (History.Entry entry : history) {
                                        if (entry != null) {
                                            out.print("<circle cx=\"");
                                            out.print(entry.x * 40 / entry.r + 50);
                                            out.print("\" cy=\"");
                                            out.print(50 - entry.y * 40 / entry.r);
                                            out.print("\" r=\"0.5\" style=\"fill: var(");
                                            out.print(entry.hit ? "--graph-hit" : "--graph-no-hit");
                                            out.println(");\" />");
                                        }
                                    }
                                }
                            %>
                        </g>
                    </svg>
                </div>
            </div>

            <div class="table_panel">
                <table id="result_table">
                    <col width="10%" span="4">
                    <col width="30%" span="2">
                    <thead>
                        <tr>
                            <th class="inverse_selected">X</th>
                            <th class="inverse_selected">Y</th>
                            <th class="inverse_selected">R</th>
                            <th class="inverse_selected">A</th>
                            <th class="inverse_selected">T</th>
                            <th class="inverse_selected">&Delta;T, ns</th>
                        </tr>
                    </thead>
                    <%
                        if (history != null) {
                            for (History.Entry entry : history) {
                                if (entry != null)
                                    out.println(entry.toHtml());
                            }
                        }
                    %>
                </table>
                <p>
                    X, Y, R&nbsp;&mdash; выбранные числа;<br>
                    A&nbsp;&mdash; попадание;<br>
                    T&nbsp;&mdash; текущее время;<br>
                    &Delta;T&nbsp;&mdash; время работы сервера от начала работы до получения этого результата.
                </p>
            </div>
        </div>
    </body>
</html>
