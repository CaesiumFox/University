function validateFinal() {
    var x_str  = document.getElementById("x_textbox").value.trim();
    var y_str  = document.getElementById("y_textbox").value.trim();
    var r1 = document.getElementById("r1_checkbox").checked;
    var r2 = document.getElementById("r2_checkbox").checked;
    var r3 = document.getElementById("r3_checkbox").checked;
    var r4 = document.getElementById("r4_checkbox").checked;
    var r5 = document.getElementById("r5_checkbox").checked;

    var acceptable = true;

    if (isNaN(x_str) || x_str === "") {
        acceptable = false;
    } else {
        acceptable = acceptable && (x_str >= -3) && (x_str <= 5);
    }
    if (isNaN(y_str) || y_str === "") {
        acceptable = false;
    } else {
        acceptable = acceptable && (y_str >= -5) && (y_str <= 3);
    }
    if (!(r1 || r2 || r3 || r4 || r5)) {
        acceptable = false;
    }

    console.log(acceptable);

    return acceptable;
}

function validateLiveX() {
    var x_str  = document.getElementById("x_textbox").value.trim();
    if (isNaN(x_str) || x_str < -3 || x_str > 5) {
        document.getElementById("x_textbox").style.color = "var(--text-wrong-color)";
        document.getElementById("text_field_x").style.setProperty("--show-x-error", "visible");
    } else {
        document.getElementById("x_textbox").style.color = "var(--text-color)";
        document.getElementById("text_field_x").style.setProperty("--show-x-error", "hidden");
    }
}
function correctX() {
    var x_str  = document.getElementById("x_textbox").value.trim();
    if (x_str == "") {
        document.getElementById("x_textbox").value = "0";
    }
}

function validateLiveY() {
    var y_str  = document.getElementById("y_textbox").value.trim();
    if (isNaN(y_str) || y_str < -5 || y_str > 3) {
        document.getElementById("y_textbox").style.color = "var(--text-wrong-color)";
        document.getElementById("text_field_y").style.setProperty("--show-y-error", "visible");
    } else {
        document.getElementById("y_textbox").style.color = "var(--text-color)";
        document.getElementById("text_field_y").style.setProperty("--show-y-error", "hidden");
    }
}
function correctY() {
    var y_str  = document.getElementById("y_textbox").value.trim();
    if (y_str == "") {
        document.getElementById("y_textbox").value = "0";
    }
}

function validateLiveR() {
    var r1 = document.getElementById("r1_checkbox").checked;
    var r2 = document.getElementById("r2_checkbox").checked;
    var r3 = document.getElementById("r3_checkbox").checked;
    var r4 = document.getElementById("r4_checkbox").checked;
    var r5 = document.getElementById("r5_checkbox").checked;
    if (!(r1 || r2 || r3 || r4 || r5)) {
        document.getElementById("r_checkbox_panel").style.setProperty("--show-r-error", "visible");
    } else {
        document.getElementById("r_checkbox_panel").style.setProperty("--show-r-error", "hidden");
    }
}
