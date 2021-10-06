<!DOCTYPE html>

<?php
    $cookie_name = 's311728_prev_table';
    $prevhrtime = intval(microtime(true) * 1e6);
    $error = false;
    $errmsg = '';
    $table = '';
    $last_cookie = 0;
    if (isset($_COOKIE[$cookie_name])) {
        foreach ($_COOKIE[$cookie_name] as $key => $row) {
            $table = $row . $table;
            if ($key > $last_cookie) {
                $last_cookie = $key;
            }
        }
    }
    $r1 = isset($_POST['r1']);
    $r2 = isset($_POST['r2']);
    $r3 = isset($_POST['r3']);
    $r4 = isset($_POST['r4']);
    $r5 = isset($_POST['r5']);

    if (isset($_POST['x']) && isset($_POST['y']) && ($r1 || $r2 || $r3 || $r4 || $r5)) {
        $x = htmlspecialchars(trim($_POST['x']));
        $y = htmlspecialchars(trim($_POST['y']));

        if (!is_numeric($x) || !is_numeric($y)) {
            $error = true;
            $errmsg .= 'x is nan | y is nan | r is empty';
        } elseif ($x < -3 || $x > 5 || $y < -5 || $y > 3) {
            $error = true;
            $errmsg .= 'x or y is out of range';
        } else {
            $table_part = '';
            for ($r = 1; $r <= 5; $r++) {
                $ptr = 'r' . $r;
                $rbool = $$ptr;
                if(!$rbool) {
                    continue;
                }
                $hit = false;
                if ($x > 0 && $y > 0) {
                    $hit = ((2 * $y <= $r) && ($x <= $r));
                }
                elseif ($x > 0 && $y < 0) {
                    $hit = (2 * $y >= $x - $r);
                }
                elseif ($x < 0 && $y < 0) {
                    $hit = (4 * ($x * $x + $y * $y) <= $r * $r);
                }
                elseif ($x == 0) {
                    $hit = ((2 * $y <= $r) && (-2 * $y <= $r));
                }
                elseif ($y == 0) {
                    $hit = (($x <= $r) && (2 * $x >= -$r));
                }
                $proctime = intval(microtime(true) * 1e6) - $prevhrtime;
                $timestr = date('Y-m-d\TH:i:s', time());


                $table_part .= '<tr>';
                $table_part .= '<td>';
                $table_part .= $x;
                $table_part .= '</td>';
                $table_part .= '<td>';
                $table_part .= $y;
                $table_part .= '</td>';
                $table_part .= '<td>';
                $table_part .= $r;
                $table_part .= '</td>';
                $table_part .= '<td>';
                $table_part .= ($hit ? 'Да' : 'Нет');
                $table_part .= '</td>';
                $table_part .= '<td>';
                $table_part .= $timestr;
                $table_part .= '</td>';
                $table_part .= '<td>';
                $table_part .= $proctime;
                $table_part .= '</td>';
                $table_part .= '</tr>';
            }
            $last_cookie++;
            setcookie($cookie_name . '[' . $last_cookie . ']', $table_part, time() + 3600 * 24);
            $table = $table_part . $table;
        }
    } else {
        $empty = true;
        $errmsg = 'Empty data!';
    }
    if (trim($table) === '') {
        $table='<tr><td class="no_queries" colspan="6">Ещё никаких запросов не было</td></tr>';
    }
?>

<html>
    <head>
        <title>Авдеев Степан P3214</title>
        <link rel="icon" href="./favicon.png">
        <style>
            :root {

                --accent-hue: 253;
                --accent-saturation: 80%;
                --accent-light-base: 58%;
                --accent-light-hover: 63%;
                --accent-light-active: 68%;
                --accent-light-border: 73%;

                --accent-color:                    hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-base));
                --bg-color:                        hsl(0, 0%, 15%);
                --header-color:                    hsl(0, 0%, 21%);
                --text-color:                      hsl(0, 0%, 90%);
                --text-wrong-color:                hsl(5, 100%, 60%);
                --text-minor-color:                hsl(0, 0%, 80%);
                --panel-color:                     hsl(0, 0%, 21%);
                --r-panel-color:                     hsl(0, 0%, 17%);
                --textfield-color:                 hsl(0, 0%, 15%);
                --textfield-input-color:           hsl(0, 0%, 5%);
                --btn-color:                       hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-base));
                --btn-hover-color:                 hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-hover));
                --btn-click-color:                 hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-active));
                --checkbox-unchecked-color:        hsl(0, 0%, 10%);
                --checkbox-unchecked-hover-color:  hsl(0, 0%, 15%);
                --checkbox-unchecked-active-color: hsl(0, 0%, 20%);
                --checkbox-unchecked-border-color: hsl(0, 0%, 30%);
                --checkbox-checked-color:          hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-base));
                --checkbox-checked-hover-color:    hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-hover));
                --checkbox-checked-active-color:   hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-active));
                --checkbox-checked-border-color:   hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-border));

                --graph-bg-color:                  hsl(0, 0%, 5%);
                --graph-border-color:              hsl(0, 0%, 20%);
                --graph-axes-color:                hsl(0, 0%, 90%);
                --graph-axes-width:                0.2;
                --graph-text-color:                var(--text-color);
                --graph-area-color:                hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-base));
                --graph-area-border-color:         hsl(var(--accent-hue), var(--accent-saturation), var(--accent-light-border));
                --graph-area-border-width:         0.2;

                --table-head-bg-color:             var(--accent-color);
                --table-head-color:                var(--text-color);
                --table-border-color:              hsl(0, 0%, 90%);
                --table-border-second-color:       hsla(0, 0%, 90%, 20%);
            }

            body {
                margin: 0px;
                padding: 0px;
                background-color: var(--bg-color);
                font-size: 16px;
                color: var(--text-color);
                font-family: sans-serif;
            }

            ::selection {
                background-color: var(--accent-color);
                color: white;
            }

            .inverse_selected::selection {
                background-color: var(--text-color);
                color: var(--accent-color);
            }

            header {
                font-size: 24px;
                font-weight: bold;
                font-family: sans-serif;
                background-color: var(--header-color);
                margin: 0px;
                padding: 1em 2em;
                border-bottom: 2px solid var(--accent-color);
            }
            header p {
                margin: 0px;
            }
            #group_label, #variant_label {
                font-weight: normal;
                color: var(--text-minor-color);
            }
            #variant_label {
                font-size: .6em;
                font-style: italic;
            }
            div.main {
                margin: 0px;
                padding: 16px 10%;
            }
            div.top_panel {
                padding: 0px 10%;
                display: grid;
                grid-template-columns: 1fr 1fr;
                column-gap: 32px;
            }

            /*-------------------------------------*/

            .form_panel {
                margin: 32px;
                padding: 24px;
                background-color: var(--panel-color);
                border-radius: 4px;
                width: max-content;
                display: grid;
                grid-template-columns: 1fr 5fr;
                grid-template-rows: repeat(4, 3em);
                gap: 16px;
            }

            .form_panel > div {
                margin: 0px;
                padding: 0px;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .form_label {
                height: 100%;
                width: 100%;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .form_label > span {
                font-weight: bolder;
                padding: 0;
                margin: 0;
                align-content: center;
            }

            .submit_button {
                height: 3em;
                color: var(--text-color);
                background-color: var(--accent-color);
                border-width: 0px;
                border-radius: 4px;
                font-weight: bolder;
                padding: 0;
                font-size: inherit;

                grid-column-start: 1;
                grid-column-end: 3;

                align-items: stretch;
                justify-content: stretch;

                transition: .2s;
            }
            .submit_button:hover {
                background-color: var(--btn-hover-color);
            }
            .submit_button:active {
                background-color: var(--btn-click-color);
            }

            .submit_button input {
                background: none;
                border: none;
                font: inherit;
                color: inherit;
                height: 100%;
                width: 100%;
                margin: 0;
                padding: 0;
            }

            .text_field input {
                height: 3em;
                width: auto;
                color: var(--text-color);
                background-color: var(--textfield-color);
                border-radius: 4px;
                border: 2px solid var(--textfield-color);
                box-shadow: none;
                padding: 0 16px;

                transition: .2s;
            }
            .text_field input:focus {
                border-color: var(--accent-color);
                background-color: var(--textfield-input-color);
                outline-style: none;
            }
            .text_field {
                flex-direction: column;
                --show-x-error: hidden;
                --show-y-error: hidden;
            }
            .text_field::after {
                height: 0;
                width: 100%;
                font-size: 10px;
                color: var(--text-wrong-color);
            }
            .text_field#text_field_x::after {
                visibility: var(--show-x-error);
                content: "Значение должно быть числом от −3 до 5";
            }
            .text_field#text_field_y::after {
                visibility: var(--show-y-error);
                content: "Значение должно быть числом от −5 до 3";
            }

            .r_panel {
                background-color: var(--r-panel-color);
                border-radius: 4px;
                flex-direction: column;
                --show-r-error: hidden;
            }
            .r_panel::after {
                height: 0;
                width: 100%;
                font-size: 10px;
                color: var(--text-wrong-color);
                visibility: var(--show-r-error);
                content: "Нужно выбрать хотя бы одно значение";
            }

            .form_panel > .r_panel > div {
                width: 100%;
                height: 100%;
                display: grid;
                grid-template-columns: repeat(5, 1fr);
            }
            .r_panel > div > div {
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .r_panel > div > div > label {
                padding-left: 6px;
                font-weight: bold;
            }

            input[type="checkbox"] {
                -webkit-appearance: none;
                -moz-appearance: none;
                font-size: inherit;
                width: 1em;
                height: 1em;
                color: var(--text-color);
                background-color: var(--checkbox-unchecked-color);
                border: 2px solid var(--checkbox-unchecked-border-color);
                border-radius: 2px;
                background-size: 80%;
                transition: .2s;
            }
            input[type="checkbox"]:hover {
                background-color: var(--checkbox-unchecked-hover-color);
            }
            input[type="checkbox"]:active {
                background-color: var(--checkbox-unchecked-active-color);
            }
            input[type="checkbox"]:checked {
                background-color: var(--checkbox-checked-color);
                border-color: var(--checkbox-checked-border-color);
                background-image: url(./tick.svg);
                background-repeat: no-repeat;
                background-position: center;
            }
            input[type="checkbox"]:checked:hover {
                background-color: var(--checkbox-checked-hover-color);
            }
            input[type="checkbox"]:checked:active {
                background-color: var(--checkbox-checked-active-color);
            }

            #graph {
                background-color: var(--graph-bg-color);
                border: 2px solid var(--graph-border-color);
                border-radius: 4px;
                display: flex;
                align-items: center;
            }

            .table_panel {
                margin: 32px;
                display: flex;
                justify-content: center;
                flex-direction: column;
                align-items: center;
            }

            #result_table {
                width: 80%;
                border-collapse: collapse;
                border: 2px solid var(--table-border-color);
            }
            #result_table th {
                text-align: center;
                background-color: var(--table-head-bg-color);
                border: 1px solid var(--table-border-color);
                color: var(--table-head-color);
                font-weight: bold;
                margin: 0;
                padding: 0.5em 1em;
            }
            #result_table td {
                border-top: 1px solid var(--table-border-color);
                border-bottom: 1px solid var(--table-border-color);
                border-right: 1px solid var(--table-border-second-color);
                border-left: 1px solid var(--table-border-second-color);
                padding: 0.5em 1em;
                text-align: center;
            }
            #result_table + p {
                width: 80%;
                font-size: 80%;
                color: var(--text-minor-color);
            }
            #result_table .no_queries {
                font-style: italic;
                color: var(--text-minor-color);
            }
        </style>

        <script type="text/javascript" src="./validation.js"></script>
    </head>
    <body onload="validateLiveX(); validateLiveY(); validateLiveR(); <?php if ($error) echo 'alert(\'Wrong data received! : ' . $errmsg . '\');'?>">
        <header>
            <p>
            Авдеев Степан Сергеевич <span id="group_label">(P3214)</span>
            </p>
            <p>
            <span id="variant_label">Вариант: 14101</span>
            </p>
        </header>
        <div class="main">
            <div class="top_panel">
                <form method="POST" onsubmit="return validateFinal()" name="numbers" action="./index.php">
                    <div class="form_panel">
                        <div class="form_label with_tool_tip"><span>X</span></div>
                        <div class="text_field" id="text_field_x">
                            <input id="x_textbox" type="text" name="x" value="<?php echo ($empty ? '0' : $x); ?>" oninput="validateLiveX();" onfocus="validateLiveX();" onchange="correctX();">
                        </div>
                        <div class="form_label"><span>Y</span></div>
                        <div class="text_field" id="text_field_y">
                            <input id="y_textbox" type="text" name="y" value="<?php echo ($empty ? '0' : $y); ?>" oninput="validateLiveY();" onfocus="validateLiveY();" onchange="correctY();">
                        </div>
                        <div class="form_label"><span>R</span></div>
                        <div class="r_panel" id="r_checkbox_panel">
                            <div>
                                <div><input type="checkbox" id="r1_checkbox" name="r1" value="r1" onclick="validateLiveR();" <?php echo ($r1 || $empty  ? 'checked' : ''); ?>><label for="r1_checkbox">1</label></div>
                                <div><input type="checkbox" id="r2_checkbox" name="r2" value="r2" onclick="validateLiveR();" <?php echo ($r2 && !$empty ? 'checked' : ''); ?>><label for="r2_checkbox">2</label></div>
                                <div><input type="checkbox" id="r3_checkbox" name="r3" value="r3" onclick="validateLiveR();" <?php echo ($r3 && !$empty ? 'checked' : ''); ?>><label for="r3_checkbox">3</label></div>
                                <div><input type="checkbox" id="r4_checkbox" name="r4" value="r4" onclick="validateLiveR();" <?php echo ($r4 && !$empty ? 'checked' : ''); ?>><label for="r4_checkbox">4</label></div>
                                <div><input type="checkbox" id="r5_checkbox" name="r5" value="r5" onclick="validateLiveR();" <?php echo ($r5 && !$empty ? 'checked' : ''); ?>><label for="r5_checkbox">5</label></div>
                            </div>
                        </div>
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

                        <path id="the_area" d="M 50 30 h 40 v 20 l -40 20 a 20 20 0 0 1 -20 -20 h 20 z" />

                        <g id="axes">
                            <path class="axis" d="M 50 95 V 5 m -1 2 l 1 -2 l 1 2"/>
                            <path class="axis" d="M 5 50 H 95 m -2 -1 l 2 1 l -2 1"/>

                            <path class="axis" d="M 50 10 m -1 0 l 2 0"/>
                            <path class="axis" d="M 50 30 m -1 0 l 2 0"/>
                            <path class="axis" d="M 50 70 m -1 0 l 2 0"/>
                            <path class="axis" d="M 50 90 m -1 0 l 2 0"/>

                            <path class="axis" d="M 10 50 m 0 -1 l 0 2"/>
                            <path class="axis" d="M 30 50 m 0 -1 l 0 2"/>
                            <path class="axis" d="M 70 50 m 0 -1 l 0 2"/>
                            <path class="axis" d="M 90 50 m 0 -1 l 0 2"/>

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
                        <th class="inverse_selected">&Delta;T, ns</th>
                    </thead>
                    <?php
                        echo $table;
                    ?>
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
