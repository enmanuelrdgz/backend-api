package surveys.utils;

import org.springframework.stereotype.Component;
import surveys.dto.OptionDTO;
import surveys.dto.SurveyDTO;
import surveys.dto.VoteDTO;
import surveys.exception.ResourceNotFoundException;
import surveys.model.Option;
import surveys.model.Survey;
import surveys.model.Vote;
import surveys.repository.OptionRepository;
import surveys.repository.SurveyRepository;
import surveys.repository.VoteRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Mapper {

    private final VoteRepository voteRepository;
    private final SurveyRepository surveyRepository;
    private final OptionRepository optionRepository;

    public Mapper(VoteRepository voteRepository, SurveyRepository surveyRepository, OptionRepository optionRepository) {
        this.voteRepository = voteRepository;
        this.surveyRepository = surveyRepository;
        this.optionRepository = optionRepository;
    }

    // Vote mappings

    public VoteDTO toVoteDTO(Vote vote) {
        return new VoteDTO(vote.getId(), vote.getSurvey().getId(), vote.getOption().getId(), vote.getIp_address());
    }

    public Vote toVoteEntity(VoteDTO voteDTO) {
        if(voteDTO.getId() != null) {
            Optional<Vote> voteEntity = voteRepository.findById(voteDTO.getId());
            if(voteEntity.isEmpty()) {
                throw new ResourceNotFoundException("Vote", voteDTO.getId());
            }
            return voteEntity.get();
        } else {
            Optional<Survey> survey = surveyRepository.findById(voteDTO.getSurvey_id());
            if(survey.isEmpty()) {
                throw new ResourceNotFoundException("Survey", voteDTO.getSurvey_id());
            }
            Optional<Option> option = optionRepository.findById(voteDTO.getOption_id());
            if(option.isEmpty()) {
                throw new ResourceNotFoundException("Option", voteDTO.getOption_id());
            }
            Vote vote = new Vote();
            vote.setIp_address(voteDTO.getIpAddress());
            vote.setSurvey(survey.get());
            vote.setOption(option.get());
            return vote;
        }
    }

    // survey mappings

    public Survey toSurveyEntity(SurveyDTO surveyDTO) {
        if(surveyDTO.getId() == null) {
            Survey survey = new Survey();
            survey.setTitle(surveyDTO.getTitle());
            List<Option> optionList = new ArrayList<>();
            for(OptionDTO optionDTO : surveyDTO.getOptions()) {
                Option option = new Option();
                option.setDescription(optionDTO.getDescription());
                option.setSurvey(survey);
                optionList.add(option);
            }
            survey.setOptions(optionList);
            survey.setActive(true);
            survey.setCreated_at(LocalDate.now());
            return survey;
        } else {
            Optional<Survey> survey = surveyRepository.findById(surveyDTO.getId());
            if(survey.isEmpty()) {
                throw new ResourceNotFoundException("Survey", surveyDTO.getId());
            }
            return survey.get();
        }
    }

    public SurveyDTO toSurveyDTO(Survey survey) {
        List<OptionDTO> optionDTOList = new ArrayList<>();
        for(Option option : survey.getOptions()) {
            OptionDTO optionDTO = new OptionDTO(option.getId(), option.getDescription(), option.getVoteCount());
            optionDTOList.add(optionDTO);
        }
        return new SurveyDTO(survey.getId(), survey.getTitle(), optionDTOList);
    }

    public List<SurveyDTO> toSurveyDTOList(List<Survey> surveyList) {
        List<SurveyDTO> surveyDTOList = new ArrayList<>();
        for(Survey survey : surveyList) {
            List<OptionDTO> optionDTOList = new ArrayList<>();
            for(Option option : survey.getOptions()) {
                OptionDTO optionDTO = new OptionDTO(option.getId(), option.getDescription(), option.getVotes().size());
                optionDTOList.add(optionDTO);
            }
            SurveyDTO surveyDTO = new SurveyDTO(survey.getId(), survey.getTitle(), optionDTOList);
            surveyDTOList.add(surveyDTO);
        }
        return surveyDTOList;
    }

    public List<Survey> toSurveyEntityList(List<SurveyDTO> surveyDTOList) {
        return new ArrayList<>();
    }
}