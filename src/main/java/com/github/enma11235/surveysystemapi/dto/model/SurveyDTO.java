package com.github.enma11235.surveysystemapi.dto.model;

import java.util.List;

public class SurveyDTO {
    private Long id;
    private String title;
    private SurveyCreator creator;
    private List<SurveyOption> options;
    private String created_at;

    public SurveyDTO(Long id, String title, SurveyCreator creator, List<SurveyOption> options, String created_at) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.options = options;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public List<SurveyOption> getOptions() {
        return options;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setOptions(List<SurveyOption> options) {
        this.options = options;
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
}
