package com.github.enma11235.surveysystemapi.dto.response;

public class CreateUserResponseBody {
    private Long id;
    private String token;

    public CreateUserResponseBody(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
