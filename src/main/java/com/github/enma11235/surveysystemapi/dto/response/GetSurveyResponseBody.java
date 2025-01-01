package com.github.enma11235.surveysystemapi.dto.response;

import com.github.enma11235.surveysystemapi.dto.model.SurveyCreator;
import com.github.enma11235.surveysystemapi.dto.model.SurveyOption;

import java.util.List;

public class GetSurveyResponseBody {
    private Long id;
    private String title;
    private SurveyCreator creator;
    private List<SurveyOption> options;
    private String created_at;

    public GetSurveyResponseBody(String created_at, List<SurveyOption> options, SurveyCreator creator, String title, Long id) {
        this.created_at = created_at;
        this.options = options;
        this.creator = creator;
        this.title = title;
        this.id = id;
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
