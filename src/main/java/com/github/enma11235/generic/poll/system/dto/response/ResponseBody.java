package com.github.enma11235.generic.poll.system.dto.response;

public class ResponseBody {
    private String message;

    public ResponseBody(String message) {
        this.message = message;
    }

    public ResponseBody(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
