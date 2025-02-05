package com.github.enma11235.generic.poll.system.dto.response;

public class LoginResponseBody extends ResponseBody {
    private String token;

    public LoginResponseBody(String message, String token) {
        super(message);
        this.token = token;
    }

    public LoginResponseBody() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
