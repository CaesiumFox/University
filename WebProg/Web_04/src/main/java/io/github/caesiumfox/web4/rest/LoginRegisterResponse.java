package io.github.caesiumfox.web4.rest;

public class LoginRegisterResponse {
    private boolean success;
    private String message;
    private String authToken;

    public LoginRegisterResponse() {}
    public LoginRegisterResponse(boolean success, String message, String authToken) {
        this.success = success;
        this.message = message;
        this.authToken = authToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
    
    public String getAuthToken() {
        return authToken;
    }
}
