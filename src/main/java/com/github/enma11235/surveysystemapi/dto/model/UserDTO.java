package com.github.enma11235.surveysystemapi.dto.model;

public class UserDTO {
    private Long id;
    private String nickname;
    private String createdAt;

    public UserDTO(Long id, String nickname, String createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

    public UserDTO(){}

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
