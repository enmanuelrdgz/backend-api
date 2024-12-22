package com.portfolio.central_server.controller;

import com.portfolio.central_server.DTO.CreateSurveyRequestDTO;
import com.portfolio.central_server.DTO.OptionDTO;
import com.portfolio.central_server.DTO.SurveyDTO;
import com.portfolio.central_server.model.*;
import com.portfolio.central_server.service.SurveyService;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    // Endpoint to get all surveys
    @GetMapping
    public ResponseEntity<List<SurveyDTO>> getAllSurveys() {
        List<Survey> surveyList = surveyService.findAllSurveys();
        List<SurveyDTO> surveyDTOList = new ArrayList<SurveyDTO>();
        for(Survey s : surveyList) {
            SurveyDTO newSurveyDTO = new SurveyDTO();
            newSurveyDTO.setId(s.getId());
            newSurveyDTO.setTitle(s.getTitle());
            newSurveyDTO.setUserNickname(s.getUser().getNickname());
            List<OptionDTO> options = new ArrayList<OptionDTO>();
            for(Option o : s.getOptions()) {
                OptionDTO newOptionDTO = new OptionDTO();
                newOptionDTO.setName(o.getName());
                newOptionDTO.setVotes(o.getVotes().size());
                options.add(newOptionDTO);
            }
            newSurveyDTO.setOptions(options);
            surveyDTOList.add(newSurveyDTO);
        }
        if(surveyDTOList.size() > 0) {
            return ResponseEntity.ok(surveyDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint to get a single survey
    @GetMapping("/{id}")
    public ResponseEntity<SurveyDTO> getSurveyById(@PathVariable Long id) {
        Optional<Survey> survey = surveyService.findSurveyById(id);
        if(survey.isPresent()) {
            SurveyDTO surveyDTO = new SurveyDTO();
            surveyDTO.setTitle(survey.get().getTitle());
            surveyDTO.setId(survey.get().getId());
            surveyDTO.setUserNickname(survey.get().getUser().getNickname());
            List<OptionDTO> options = new ArrayList<OptionDTO>();
            for(Option o : survey.get().getOptions()) {
                OptionDTO newOptionDTO = new OptionDTO();
                newOptionDTO.setName(o.getName());
                newOptionDTO.setVotes(o.getVotes().size());
                options.add(newOptionDTO);
            }
            surveyDTO.setOptions(options);
            return ResponseEntity.ok(surveyDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint to create a new survey
    @PostMapping
    public ResponseEntity<Void> createSurvey(@RequestBody CreateSurveyRequestDTO request) {

        Survey newSurvey = new Survey();
        newSurvey.setTitle(request.getTitle());
        List<Option> options = new ArrayList<Option>();
        for(String name : request.getOptionNames()) {
            Option newOption = new Option();
            newOption.setSurvey(newSurvey);
            newOption.setName(name);
            options.add(newOption);
        }
        newSurvey.setOptions(options);
        try {
            surveyService.saveSurvey(newSurvey);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PersistenceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to delete a survey by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurveyById(id);
        return ResponseEntity.noContent().build();
    }
}