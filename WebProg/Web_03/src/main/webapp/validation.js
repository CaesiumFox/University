let r = 2;

function validateFinal() {
    let y_str  = document.getElementById("y_text").value.trim();
    let r_str  = document.getElementById("r_text").value.trim();

    let acceptable = true;

    if (isNaN(y_str) || y_str === "") {
        acceptable = false;
    } else {
        acceptable = acceptable && (y_str >= -5) && (y_str <= 3);
    }

    if (isNaN(r_str) || r_str === "") {
        acceptable = false;
    } else {
        acceptable = acceptable && (r_str >= 2) && (r_str <= 5);
    }

    console.log(acceptable);

    return acceptable;
}

function validateLiveY() {
    let y_str  = document.getElementById("y_text").value.trim();
    if (isNaN(y_str) || y_str < -5 || y_str > 3) {
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

function validateLiveR() {
    let r_str  = document.getElementById("r_text").value.trim();
    if (isNaN(r_str) || r_str < 2 || r_str > 5) {
        document.getElementById("r_text").style.color = "var(--text-wrong-color)";
        document.getElementById("text_field_r").style.setProperty("--show-r-error", "visible");
    } else {
        document.getElementById("r_text").style.color = "var(--text-color)";
        document.getElementById("text_field_r").style.setProperty("--show-r-error", "hidden");
    }
}
function correctR() {
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

function onbodyload() {
    validateLiveY();
    validateLiveR();
    correctY();
    correctR();
}

function setImageCoordinates(event) {
    correctR();
    let rect = document.getElementById("the_image").getBoundingClientRect();
    let mx = event.offsetX;
    let my = event.offsetY;
    let iw = rect.width;
    let ih = rect.height;

    let x = (10 * mx / iw - 5) * r / 4; // (mx / iw - 0.5) * 2 * 5 * r / 4
    let y = (5 - 10 * my / ih) * r / 4; // (0.5 - mx / iw) * 2 * 5 * r / 4

    let imgX = document.getElementById("img_x");
    let imgY = document.getElementById("img_y");
    imgX.setAttribute("value", x.toString());
    imgY.setAttribute("value", y.toString());
}