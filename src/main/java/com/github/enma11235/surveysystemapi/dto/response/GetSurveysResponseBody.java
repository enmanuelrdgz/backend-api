package com.github.enma11235.surveysystemapi.dto.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class Creator {
    private long id;
    private String nickname;
    private String image;

    public Creator(long id, String nickname, String image) {
        this.id = id;
        this.nickname = nickname;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

class Option {
    private long id;
    private String name;
    private int votes;

    public Option(long id, String name, int votes) {
        this.id = id;
        this.name = name;
        this.votes = votes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}

public class GetSurveysResponseBody {
    private long id;
    private String title;
    private Creator creator;
    private List<Option> options;

    public GetSurveysResponseBody(long id, String title, HashMap<String, Object> creator, List<HashMap<String, Object>> _options) {
        this.id = id;
        this.title = title;
        this.creator = new Creator((long) creator.get("id"), (String) creator.get("nickname"), (String) creator.get("image"));
        List<Option> options = new ArrayList<Option>();
        for(HashMap map : _options) {
            options.add(new Option((long) map.get("id"), (String) map.get("name"), (int) map.get("votes")));
        }
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
