package com.github.enma11235.surveysystemapi.dto.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoteResponseBody {

    private long id;
    private String title;
    private Creator creator;
    private List<Option> options;
    private int total_votes;
    private String created_at;


    public VoteResponseBody(long id, String title, HashMap<String, Object> creator, List<HashMap<String, Object>> _options, int total_votes, String created_at) {
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
