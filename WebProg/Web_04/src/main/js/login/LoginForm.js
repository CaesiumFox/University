import React, { useState } from 'react';
import $ from 'jquery';

export default function LoginForm(props) {
    let [user, setUser] = useState("");
    let [pass, setPass] = useState("");

    $("#login_form").css("--show-username-error", "hidden");
    $("#login_form").css("--show-password-error", "hidden");

    if (user.length < 3) {
        $("#username_field").css("color", "var(--text-wrong-color)");
        $("#login_form").css("--show-username-error", "visible");
        $("#login_form").css("--text-username-error",
            "\"Имя пользователя должно содержать от 3-х до 64-х символов\"");
    }
    else if (!/^\w{1,64}$/.test(user)) {
        $("#username_field").css("color", "var(--text-wrong-color)");
        $("#login_form").css("--show-username-error", "visible");
        $("#login_form").css("--text-username-error",
            "\"Имя пользователя должно содержать только символы 'a'-'z', 'A'-'Z' и '_'\"");
    }
    else {
        $("#username_field").css("color", "var(--text-color)");
        $("#login_form").css("--show-username-error", "hidden");
    }
    
    if (pass.length < 4) {
        $("#password_field").css("color", "var(--text-wrong-color)");
        $("#login_form").css("--show-password-error", "visible");
    }
    else {
        $("#password_field").css("color", "var(--text-color)");
        $("#login_form").css("--show-password-error", "hidden");
    }

    function register(e) {
        e.preventDefault();
        $.post({
            url: "/register",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                username: user,
                password: pass
            })
        }).done(function(data) {
            if (data.success) {
                location.reload();
            }
            else {
                switch(data.message) {
                    case "USERNAME_EXISTS":
                        $("#login_form").css("--show-username-error", "visible");
                        $("#login_form").css("--text-username-error",
                            "\"Пользователь с этим именем уже существует\"");
                        break;
                    case "SHORT_PASSWORD":
                    case "LONG_PASSWORD":
                        $("#login_form").css("--show-password-error", "visible");
                        $("#login_form").css("--text-password-error",
                            "\"Пароль должен содержать от 4-х до 64-х символов\"");
                        break;
                    case "SHORT_USERNAME":
                    case "LONG_USERNAME":
                        $("#login_form").css("--show-username-error", "visible");
                        $("#login_form").css("--text-username-error",
                            "\"Имя пользователя должно содержать от 3-х до 64-х символов\"");
                        break;
                    case "INVALID_USERNAME":
                        $("#login_form").css("--show-username-error", "visible");
                        $("#login_form").css("--text-username-error",
                            "\"Имя пользователя должно содержать только символы 'a'-'z', 'A'-'Z' и '_'\"");
                        break;
                }
            }
        }).fail(function(jqXHR, textStatus) {
            alert("Sending ajax failed: " + textStatus);
        });
    }

    function login(e) {
        e.preventDefault();
        $.post({
            url: "/login",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                username: user,
                password: pass
            })
        }).done(function(data) {
            if (data.success) {
                location.reload();
            }
            else {
                switch(data.message) {
                    case "WRONG_USERNAME":
                        $("#login_form").css("--show-username-error", "visible");
                        $("#login_form").css("--text-username-error", "\"Пользователя с таким именем не существует\"");
                        break;
                    case "WRONG_PASSWORD":
                        $("#login_form").css("--show-password-error", "visible");
                        $("#login_form").css("--text-password-error", "\"Неверный пароль\"");
                        break;
                }
            }
        }).fail(function(jqXHR, textStatus) {
            alert("Sending ajax failed: " + textStatus);
        });
    }

    return (
        <form id="login_form">
            <div>
                <div className="text_field" id="username_field">
                    <input type="text" placeholder="Имя пользователя" maxLength="64"
                        value={user}
                        onInput={(e) => {setUser(e.target.value)}}/>
                </div>
                <div className="text_field" id="password_field">
                    <input type="password" placeholder="Пароль" maxLength="64"
                        value={pass}
                        onInput={(e) => {setPass(e.target.value)}}/>
                </div>
                <div className="submit_button">
                    <input type="submit" value="Войти" onClick={login}/>
                </div>
                <div className="submit_button">
                    <input type="submit" value="Зарегистрироваться" onClick={register}/>
                </div>
            </div>
        </form>
    );
}