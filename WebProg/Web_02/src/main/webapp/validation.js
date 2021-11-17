function validateFinal() {
    let xm3 = document.getElementById("xm3_checkbox").checked;
    let xm2 = document.getElementById("xm2_checkbox").checked;
    let xm1 = document.getElementById("xm1_checkbox").checked;
    let x0 = document.getElementById("x0_checkbox").checked;
    let x1 = document.getElementById("x1_checkbox").checked;
    let x2 = document.getElementById("x2_checkbox").checked;
    let x3 = document.getElementById("x3_checkbox").checked;
    let x4 = document.getElementById("x4_checkbox").checked;
    let x5 = document.getElementById("x5_checkbox").checked;
    let y_str  = document.getElementById("y_text").value.trim();

    let acceptable = true;

    if (!(xm3 || xm2 || xm1 || x0 || x1 || x2 || x3 || x4 || x5)) {
        acceptable = false;
    }
    if (isNaN(y_str) || y_str === "") {
        acceptable = false;
    } else {
        acceptable = acceptable && (y_str >= -3) && (y_str <= 5);
    }

    console.log(acceptable);

    return acceptable;
}

function validateLiveX() {
    let xm3 = document.getElementById("xm3_checkbox").checked;
    let xm2 = document.getElementById("xm2_checkbox").checked;
    let xm1 = document.getElementById("xm1_checkbox").checked;
    let x0 = document.getElementById("x0_checkbox").checked;
    let x1 = document.getElementById("x1_checkbox").checked;
    let x2 = document.getElementById("x2_checkbox").checked;
    let x3 = document.getElementById("x3_checkbox").checked;
    let x4 = document.getElementById("x4_checkbox").checked;
    let x5 = document.getElementById("x5_checkbox").checked;
    if (!(xm3 || xm2 || xm1 || x0 || x1 || x2 || x3 || x4 || x5)) {
        document.getElementById("x_checkbox_panel").style.setProperty("--show-x-error", "visible");
    } else {
        document.getElementById("x_checkbox_panel").style.setProperty("--show-x-error", "hidden");
    }
}

function validateLiveY() {
    let y_str  = document.getElementById("y_text").value.trim();
    if (isNaN(y_str) || y_str < -3 || y_str > 5) {
        document.getElementById("y_text").style.color = "var(--text-wrong-color)";
        document.getElementById("text_field_y").style.setProperty("--show-y-error", "visible");
    } else {
        document.getElementById("y_text").style.color = "var(--text-color)";
        document.getElementById("text_field_y").style.setProperty("--show-y-error", "hidden");
    }
}
function correctY() {
    let y_str  = document.getElementById("y_text").value.trim();
    if (y_str === "") {
        document.getElementById("y_text").value = "0";
    }
}

function pressR(arg) {
    if (arg >= 1 && arg <= 5) {
        document.getElementById("r_text").innerHTML = "R = 1" + arg;
        document.getElementById("r_field").setAttribute("value", arg);
    }
}
