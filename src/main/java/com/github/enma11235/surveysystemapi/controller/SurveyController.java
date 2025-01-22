package com.github.enma11235.surveysystemapi.controller;

import com.github.enma11235.surveysystemapi.dto.request.CreateSurveyRequestBody;
import com.github.enma11235.surveysystemapi.dto.model.SurveyOption;
import com.github.enma11235.surveysystemapi.dto.model.SurveyDTO;
import com.github.enma11235.surveysystemapi.dto.request.VoteRequestBody;
import com.github.enma11235.surveysystemapi.dto.response.CreateSurveyResponseBody;
import com.github.enma11235.surveysystemapi.dto.response.GetSurveyResponseBody;
import com.github.enma11235.surveysystemapi.dto.response.GetSurveysResponseBody;
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

    // CREATE SURVEY
    @PostMapping
    public ResponseEntity<CreateSurveyResponseBody> createSurvey(@RequestBody @Valid CreateSurveyRequestBody body) {
        //obtenemos el token
        String token = body.getToken();
        SurveyDTO survey = surveyService.createSurvey(body.getTitle(), body.getOptions(), token);
        CreateSurveyResponseBody responseBody = new CreateSurveyResponseBody(survey.getId(), survey.getTitle(), survey.getCreator(), survey.getOptions(), survey.getCreated_at(), 0);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    // GET SURVEY
    @GetMapping("/{id}")
    public ResponseEntity<GetSurveyResponseBody> getSurveyById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        //obtenemos el token
        String token = authorizationHeader.substring(7);
        SurveyDTO surveyDTO = surveyService.getSurveyById(id, token);
        GetSurveyResponseBody responseBody = new GetSurveyResponseBody(surveyDTO.getId(), surveyDTO.getTitle(), surveyDTO.getCreator(), surveyDTO.getOptions(), surveyDTO.getCreated_at());
        return ResponseEntity.ok(responseBody);
    }

    //GET ALL SURVEYS
    @GetMapping
    public ResponseEntity<List<GetSurveysResponseBody>> getSurveys() {
        List<GetSurveysResponseBody> responseBody = surveyService.getAllSurveys();
        return ResponseEntity.ok(responseBody);
    }

    //VOTE
    @PostMapping("/{survey_id}/{option_id}")
    public ResponseEntity<Void> vote(@PathVariable Long survey_id, @PathVariable Long option_id, @RequestBody @Valid VoteRequestBody body) {
        surveyService.vote(survey_id, option_id, body.getToken());
        return ResponseEntity.ok().build();
    }
}