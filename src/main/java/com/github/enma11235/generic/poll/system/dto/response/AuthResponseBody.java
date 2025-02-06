package com.github.enma11235.generic.poll.system.dto.response;

public class AuthResponseBody extends ResponseBody{
    private String token;

    public AuthResponseBody(String message, String token) {
        super(message);
        this.token = token;
    }

    public AuthResponseBody() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
