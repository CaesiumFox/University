package io.github.caesiumfox.web4.rest;

public class LoginRegisterRequest {
    private String username;
    private String password;

    public LoginRegisterRequest() {}
    public LoginRegisterRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
