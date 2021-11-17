<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%! boolean error = false; %>
<%! String errs = ""; %>

<!DOCTYPE html>
<html>
    <head>
        <title>Авдеев Степан P3214</title>
        <link rel="icon" href="/favicon.png">
        <link rel="stylesheet" href="/allstyles.css">
        <script type="text/javascript" src="/validation.js"></script>
    </head>
    <body onload="validateLiveX(); validateLiveY(); <%= error ? ("alert('Wrong data received! : " + errs + "');") : "" %>">
        <header>
            <p>
            Авдеев Степан Сергеевич <span id="group_label">(P3214)</span>
            </p>
            <p>
            <span id="variant_label">Вариант: 3662</span>
            </p>
        </header>
        <div class="main">
            <div class="top_panel">
                <form method="POST" onsubmit="return validateFinal()" name="numbers" action="/">
                    <div class="form_panel">
                        <div class="form_label with_tool_tip"><span>X</span></div>
                        <div class="x_panel" id="x_checkbox_panel">
                            <div>
                                <div><input type="checkbox" id="xm3_checkbox" name="xm3" value="xm3" onclick="validateLiveX();"><label for="xm3_checkbox">&minus;3</label></div>
                                <div><input type="checkbox" id="xm2_checkbox" name="xm2" value="xm2" onclick="validateLiveX();"><label for="xm2_checkbox">&minus;2</label></div>
                                <div><input type="checkbox" id="xm1_checkbox" name="xm1" value="xm1" onclick="validateLiveX();"><label for="xm1_checkbox">&minus;1</label></div>
                                <div><input type="checkbox" id="x0_checkbox" name="x0" value="x0" onclick="validateLiveX();"><label for="x0_checkbox">0</label></div>
                                <div><input type="checkbox" id="x1_checkbox" name="x1" value="x1" onclick="validateLiveX();"><label for="x1_checkbox">1</label></div>
                                <div><input type="checkbox" id="x2_checkbox" name="x2" value="x2" onclick="validateLiveX();"><label for="x2_checkbox">2</label></div>
                                <div><input type="checkbox" id="x3_checkbox" name="x3" value="x3" onclick="validateLiveX();"><label for="x3_checkbox">3</label></div>
                                <div><input type="checkbox" id="x4_checkbox" name="x4" value="x4" onclick="validateLiveX();"><label for="x4_checkbox">4</label></div>
                                <div><input type="checkbox" id="x5_checkbox" name="x5" value="x5" onclick="validateLiveX();"><label for="x5_checkbox">5</label></div>
                            </div>
                        </div>
                        <div class="form_label"><span>Y</span></div>
                        <div class="text_field" id="text_field_y">
                            <input id="y_text" type="text" maxlength="20" name="y" value="0" oninput="validateLiveY();" onfocus="validateLiveY();" onchange="correctY();">
                        </div>
                        <div class="form_label"><span id="r_text">R = 1</span></div>
                        <div class="r_panel" id="r_button_panel">
                            <div>
                                <div><input type="button" id="r1_button" name="r1" value="1" onclick="pressR(1);"></div>
                                <div><input type="button" id="r2_button" name="r2" value="2" onclick="pressR(2);"></div>
                                <div><input type="button" id="r3_button" name="r3" value="3" onclick="pressR(3);"></div>
                                <div><input type="button" id="r4_button" name="r4" value="4" onclick="pressR(4);"></div>
                                <div><input type="button" id="r5_button" name="r5" value="5" onclick="pressR(5);"></div>
                            </div>
                        </div>
                        <input type="hidden" id="r_field" name="r" value="1" />
                        <div class="submit_button"><input type="submit" value="Отправить"></div>
                    </div>
                </form>
                <div id="graph">
                    <svg width="100%" viewbox="0 0 100 100">
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

                        <path id="the_area" d="M 50 30 h 40 v 20 l -40 20 a 20 20 0 0 1 -20 -20 h 20 z"></path>

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
                            <text class="x_axis" x="10" y="50" dy="4">&minus;R/2</text>

                            <text class="y_axis" x="50" y="10" dx="-2" dy="1">R</text>
                            <text class="y_axis" x="50" y="30" dx="-2" dy="1">R/2</text>
                            <text class="y_axis" x="50" y="70" dx="-2" dy="1">&minus;R/2</text>
                            <text class="y_axis" x="50" y="90" dx="-2" dy="1">&minus;R/2</text>

                            <text class="y_axis" x="50" y="50" dx="-2" dy="4">O</text>
                        </g>
                    </svg>
                </div>
            </div>

            <div class="table_panel">
                <table id="result_table">
                    <col width="10%" span="4">
                    <col width="30%" span="2">
                    <thead>
                        <th class="inverse_selected">X</th>
                        <th class="inverse_selected">Y</th>
                        <th class="inverse_selected">R</th>
                        <th class="inverse_selected">A</th>
                        <th class="inverse_selected">T</th>
                        <th class="inverse_selected">&Delta;T, &micro;s</th>
                    </thead>
                </table>
                <p>
                    X, Y, R&nbsp;&mdash; выбранные числа;<br>
                    A&nbsp;&mdash; попадание;<br>
                    T&nbsp;&mdash; текущее время;<br>
                    &Delta;T&nbsp;&mdash; время работы скрипта от начала работы до получения этого результата.
                </p>
            </div>
        </div>
    </body>
</html>
