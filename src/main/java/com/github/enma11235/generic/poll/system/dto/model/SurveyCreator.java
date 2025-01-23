package com.github.enma11235.generic.poll.system.dto.model;

public class SurveyCreator {
    private Long id;
    private String nickname;

    public SurveyCreator(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
