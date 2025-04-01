package surveys.model;
import surveys.model.dto.SurveyDTO;
import surveys.exception.BusinessRuleViolationException;
import surveys.exception.ResourceNotFoundException;
import surveys.model.dto.VoteDTO;
import surveys.model.entity.Option;
import surveys.model.entity.Survey;
import surveys.model.entity.Vote;
import surveys.model.repository.OptionRepository;
import surveys.model.repository.SurveyRepository;
import surveys.model.repository.VoteRepository;
import org.springframework.beans.factory.annotation.*;
import surveys.utils.SurveyMapper;

import java.util.*;

@org.springframework.stereotype.Service
public class Service {

    private final SurveyRepository surveyRepository;
    private final OptionRepository optionRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public Service(SurveyRepository surveyRepository, OptionRepository optionRepository, VoteRepository voteRepository) {
        this.surveyRepository = surveyRepository;
        this.optionRepository = optionRepository;
        this.voteRepository = voteRepository;
    }

    public SurveyDTO createNewSurvey(String title, List<String> options) {
        Survey savedSurvey = surveyRepository.save(new Survey(title, options));
        return SurveyMapper.INSTANCE.toSurveyDTO(savedSurvey);
    }

    public List<SurveyDTO> getActiveSurveys() {
        List<Survey> activeSurveyList = surveyRepository.findByActive(true);
        return SurveyMapper.INSTANCE.toSurveyDTOList(activeSurveyList);
    }

    public VoteDTO createNewVote(long survey_id, long option_id, String ip_address) {

        Optional<Option> option = optionRepository.findById(option_id);
        Optional<Survey> survey = surveyRepository.findById(survey_id);

        if(survey.isEmpty()) {
            throw new ResourceNotFoundException("Survey", survey_id);
        } else if(option.isEmpty()) {
            throw new ResourceNotFoundException("Option", option_id);
        } else if(!survey.get().getOptions().contains(option.get())) {
            throw new BusinessRuleViolationException("Option with ID " + option_id + " doesn't belong to Survey with ID " + survey_id);
        } else {
            Vote newVote = new Vote(survey.get(), option.get(), ip_address);

            // Esto puede lanzar una DataIntegrityViolationException si
            // el usuario ya ha votado antes en misma la encuesta.
            Vote savedVote = voteRepository.save(newVote);

            return SurveyMapper.INSTANCE.toVoteDTO(savedVote);
        }
    }
}
