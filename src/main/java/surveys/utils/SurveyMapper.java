package surveys.utils;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import surveys.model.dto.OptionDTO;
import surveys.model.dto.SurveyDTO;
import surveys.model.dto.VoteDTO;
import surveys.model.entity.Option;
import surveys.model.entity.Survey;
import surveys.model.entity.Vote;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

    SurveyMapper INSTANCE = Mappers.getMapper(SurveyMapper.class);

    @Mapping(target = "optionList", source = "options")
    SurveyDTO toSurveyDTO(Survey survey);

    List<SurveyDTO> toSurveyDTOList(List<Survey> surveys);

    List<OptionDTO> toOptionDTOList(List<Option> options);

    @Mapping(target = "voteCount", expression = "java(option.getVotes() != null ? option.getVotes().size() : 0)")
    OptionDTO toOptionDTO(Option option);

    @Mapping(target = "survey_id", source = "survey.id")
    @Mapping(target = "option_id", source = "option.id")
    VoteDTO toVoteDTO(Vote vote);
}
