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
    private int total_votes;
    private String created_at;

    public GetSurveysResponseBody(long id, String title, HashMap<String, Object> creator, List<HashMap<String, Object>> _options, int total_votes, String created_at) {
        this.id = id;
        this.title = title;
        this.creator = new Creator((long) creator.get("id"), (String) creator.get("nickname"), (String) creator.get("image"));
        List<Option> options = new ArrayList<Option>();
        for(HashMap map : _options) {
            options.add(new Option((long) map.get("id"), (String) map.get("name"), (int) map.get("votes")));
        }
        this.options = options;
        this.total_votes = total_votes;
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getTotal_votes() {
        return total_votes;
    }

    public void setTotal_votes(int total_votes) {
        this.total_votes = total_votes;
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
