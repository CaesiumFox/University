import React from "react";
import { useSelector } from "react-redux";
import $ from 'jquery';

export default function LogoutButton(props) {
    let username = useSelector(state => state.username);
    function logout(e) {
        $.post({
            url: "/logout"
        }).done(function(data) {
            location.reload();
        }).fail(function(jqXHR, textStatus) {
            alert("Sending ajax failed: " + textStatus);
        });
    }

    return (<p class="username_label">
        Вы вошли как <span>{username}</span>
        <input type="button"
            onClick={logout}
            value="Выйти из аккаунта">
        </input>
    </p>);
}