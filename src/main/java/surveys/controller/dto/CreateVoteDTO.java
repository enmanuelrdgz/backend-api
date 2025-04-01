package surveys.controller.dto;

import jakarta.validation.constraints.*;

public class CreateVoteDTO {

    @NotNull
    @Min(0)
    private long survey_id;
    @NotNull
    @Min(0)
    private long option_id;

    public long getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(long survey_id) {
        this.survey_id = survey_id;
    }

    public long getOption_id() {
        return option_id;
    }

    public void setOption_id(long option_id) {
        this.option_id = option_id;
    }
}
