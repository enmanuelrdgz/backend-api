package com.github.enma11235.surveysystemapi.service;

import com.github.enma11235.surveysystemapi.model.Survey;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.github.enma11235.surveysystemapi.repository.SurveyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    //method to find a survey by ID
    public Optional<Survey> findSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    //method to get all surveys
    public List<Survey> findAllSurveys(){
        return surveyRepository.findAll();
    }

    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public void deleteSurveyById(Long id) {
        surveyRepository.deleteById(id);
    }
}
