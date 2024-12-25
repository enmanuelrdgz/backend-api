package com.portfolio.central_server.DTO;

import java.util.*;

public class CreateSurveyRequestDTO {

    private String title;
    private String user;
    private List<String> options;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title;}

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
