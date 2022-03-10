import Header from "./common/Header";
import {useState} from "react";

function Timer() {
    return <p><span id="timer">01.01.1970 00:00:00</span></p>
}

function LoginForm() {
    let [username, setUsername] = useState("");
    let [password, setPassword] = useState("");

    function submit(event) {
        event.preventDefault();
        if (event.submitter.name === "login") {
            $.post({
                url: "/login",
                dataType: "json",
                data: {
                    username: username,
                    password: password
                }
            }).done(function(data) {
                if (data.success === true) {
                    $("#login_message").innerHTML = "";
                    location.replace("/app");
                }
                else {
                    $("#login_message").innerHTML = data.message;
                }
            }).fail(function(jqXHR, textStatus) {
                alert('Sending ajax failed: ' + textStatus);
            });
        }
        else {
            $.post({
                url: "/register",
                dataType: "json",
                data: {
                    username: username,
                    password: password
                }
            }).done(function(data) {
                if (data.success === true) {
                    $("#login_message").innerHTML = "";
                    location.replace("/app");
                }
                else {
                    $("#login_message").innerHTML = data.message;
                }
            }).fail(function(jqXHR, textStatus) {
                alert('Sending ajax failed: ' + textStatus);
            });
        }
    }

    return (<form onSubmit={submit} id="login_form">
        <p>Имя пользователя:</p>
        <input type="text" id="username_input" maxLength="64" name="username" value=""
               onChange={(event)=>{
                   setUsername(event.target.value);
               }}/>
        <p>Пароль:</p>
        <input type="password" id="password_input" maxLength="64" name="password" value=""
               onChange={(event)=>{
                   setPassword(event.target.value);
               }}/>
        <input type="submit" name="login" value="Войти"/>
        <input type="submit" name="register" value="Зарегистрироваться"/>
        <p id="login_massage"></p>
    </form>);
}

export default function Welcome() {
    return (<>
        <Header name="Авдеев Степан Сергеевич"
            group="P3214"
            variant="81863"/>
        <div className="main">
            <Timer/>
            <LoginForm/>
        </div>
    </>);
}
