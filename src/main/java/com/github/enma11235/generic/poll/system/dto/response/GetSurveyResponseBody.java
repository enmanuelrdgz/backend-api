package com.github.enma11235.generic.poll.system.dto.response;

import com.github.enma11235.generic.poll.system.dto.model.SurveyCreator;
import com.github.enma11235.generic.poll.system.dto.model.SurveyOption;

import java.util.List;

public class GetSurveyResponseBody {
    private Long id;
    private String title;
    private SurveyCreator creator;
    private List<SurveyOption> options;
    private String created_at;

    public GetSurveyResponseBody(Long id, String title, SurveyCreator creator, List<SurveyOption> options, String created_at) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.options = options;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SurveyCreator getCreator() {
        return creator;
    }

    public void setCreator(SurveyCreator creator) {
        this.creator = creator;
    }

    public List<SurveyOption> getOptions() {
        return options;
    }

    public void setOptions(List<SurveyOption> options) {
        this.options = options;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
