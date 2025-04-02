package surveys.service;

import surveys.dto.SurveyDTO;
import surveys.model.Survey;
import surveys.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.*;
import surveys.utils.Mapper;

import java.util.*;

@org.springframework.stereotype.Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final Mapper mapper;


    @Autowired
    public SurveyService(SurveyRepository surveyRepository, Mapper mapper) {
        this.surveyRepository = surveyRepository;
        this.mapper = mapper;
    }

    public SurveyDTO createNewSurvey(SurveyDTO surveyDTO) {
        Survey survey = mapper.toSurveyEntity(surveyDTO);
        Survey savedSurvey = surveyRepository.save(survey);
        return mapper.toSurveyDTO(savedSurvey);
    }

    public List<SurveyDTO> getActiveSurveys() {
        List<Survey> surveyList = surveyRepository.findByActive(true);
        return mapper.toSurveyDTOList(surveyList);
    }
}
