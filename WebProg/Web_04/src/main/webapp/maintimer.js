function updateTime() {
    let field = document.getElementById("timer");
    let time = new Date(Date.now());
    let outstr = twoDigit(time.getDate()) + "."
        + twoDigit(time.getMonth() + 1) + "."
        + time.getFullYear() + " ";
    outstr += twoDigit(time.getHours()) + ":"
        + twoDigit(time.getMinutes()) + ":"
        + twoDigit(time.getSeconds());
    field.innerHTML = outstr;
}

function twoDigit(value) {
    if (value < 10)
        return "0" + value.toString();
    else
        return value.toString();
}
