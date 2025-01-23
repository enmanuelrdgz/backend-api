package com.github.enma11235.generic.poll.system.dto.response;

public class GetUserResponseBody {
    private Long id;
    private String nickname;
    private String password;
    private String image;

    public GetUserResponseBody(Long id, String nickname, String password, String image) {
        this.id = id;
        this.nickname = nickname;
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
