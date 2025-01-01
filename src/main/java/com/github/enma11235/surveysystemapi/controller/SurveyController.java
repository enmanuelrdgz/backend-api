package com.github.enma11235.surveysystemapi.controller;

import com.github.enma11235.surveysystemapi.dto.request.CreateSurveyRequestBody;
import com.github.enma11235.surveysystemapi.dto.model.SurveyOption;
import com.github.enma11235.surveysystemapi.dto.model.SurveyDTO;
import com.github.enma11235.surveysystemapi.dto.response.CreateSurveyResponseBody;
import com.github.enma11235.surveysystemapi.model.Option;
import com.github.enma11235.surveysystemapi.model.Survey;
import com.github.enma11235.surveysystemapi.service.SurveyService;
import com.github.enma11235.surveysystemapi.service.UserService;
import jakarta.validation.Valid;
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
    public SurveyController(SurveyService surveyService, AuthController authController, UserService userService) {
        this.surveyService = surveyService;
    }

    // Endpoint to get all surveys
//    @GetMapping
//    public ResponseEntity<List<SurveyDTO>> getAllSurveys() {
//
//    }

    // GET SURVEY
//    @GetMapping("/{id}")
//    public ResponseEntity<SurveyDTO> getSurveyById(@PathVariable Long id) {
//
//    }

    // CREATE SURVEY
    @PostMapping
    public ResponseEntity<CreateSurveyResponseBody> createSurvey(@RequestBody @Valid CreateSurveyRequestBody body, @RequestHeader("Authorization") String authorizationHeader) {
        //obtenemos el token
        String token = authorizationHeader.substring(7);
        SurveyDTO survey = surveyService.createSurvey(body.getTitle(), body.getOptions(), token);
        CreateSurveyResponseBody responseBody = new CreateSurveyResponseBody(survey.getId(), survey.getTitle(), survey.getCreator(), survey.getOptions(), survey.getCreated_at());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    // Endpoint to delete a survey by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurveyById(id);
        return ResponseEntity.noContent().build();
    }
}