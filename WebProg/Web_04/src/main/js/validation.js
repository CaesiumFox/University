let imgR = 0;

function validateFinal() {
    let acceptable = checkX() && checkY() && checkR();
    console.log(acceptable);
    return acceptable;
}

function checkY() {
    let y_str = jQuery("#y_text").val().trim();
    return (y_str !== "") && !isNaN(y_str) && y_str >= -3 && y_str <= 5;
}

function validateLiveY() {
    if (!checkY()) {
        jQuery("#y_text").css("color", "var(--text-wrong-color)");
        jQuery("#text_field_y").css("--show-y-error", "visible");
    } else {
        jQuery("#y_text").css("color", "var(--text-color)");
        jQuery("#text_field_y").css("--show-y-error", "hidden");
    }
}
function correctY() {
    if (jQuery("#y_text").val().trim() === "") {
        jQuery("#y_text").val("0");
    }
}
/*
function validateLiveImgR() {
    let r_str  = document.getElementById("r_text").value.trim();
    if (isNaN(r_str) || r_str < 2 || r_str > 5) {
        document.getElementById("r_text").style.color = "var(--text-wrong-color)";
        document.getElementById("text_field_r").style.setProperty("--show-r-error", "visible");
    } else {
        document.getElementById("r_text").style.color = "var(--text-color)";
        document.getElementById("text_field_r").style.setProperty("--show-r-error", "hidden");
    }
}
function correctImgR() {
    let r_str  = document.getElementById("r_text").value.trim();
    if (r_str === "") {
        document.getElementById("r_text").value = "2";
        document.getElementById("r_text").style.color = "var(--text-color)";
        document.getElementById("text_field_r").style.setProperty("--show-r-error", "hidden");
    }
    if (!isNaN(r_str) && r_str >= 2 && r_str <= 5) {
        r = parseInt(r_str);
    }
}
*/
function onBodyLoad() {
    validateLiveY();
    validateLiveR();
    correctY();
    correctR();
}

function setImageCoordinates(event) {
    correctR();
    let rect = jQuery("#the_image").getBoundingClientRect();
    let mx = event.offsetX;
    let my = event.offsetY;
    let iw = rect.width;
    let ih = rect.height;

    let x = (10 * mx / iw - 5) * r / 4; // (mx / iw - 0.5) * 2 * 5 * r / 4
    let y = (5 - 10 * my / ih) * r / 4; // (0.5 - mx / iw) * 2 * 5 * r / 4

    jQuery("#img_x").val(x.toString());
    jQuery("#img_y").val(y.toString());
}