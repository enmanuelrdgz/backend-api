package com.github.enma11235.surveysystemapi.dto.model;

public class UserDTO {
    private Long id;
    private String nickname;
    private String createdAt;
    private String password;
    private String image;

    public UserDTO(Long id, String nickname, String createdAt, String password, String image) {
        this.id = id;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.password = password;
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
