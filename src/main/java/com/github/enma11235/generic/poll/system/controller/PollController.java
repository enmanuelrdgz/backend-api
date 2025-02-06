package com.github.enma11235.generic.poll.system.controller;

import com.github.enma11235.generic.poll.system.dto.model.PollData;
import com.github.enma11235.generic.poll.system.dto.request.CreateSurveyRequestBody;
import com.github.enma11235.generic.poll.system.dto.request.VoteRequestBody;
import com.github.enma11235.generic.poll.system.dto.response.CreateSurveyResponseBody;
import com.github.enma11235.generic.poll.system.dto.response.GetAllPollsResponseBody;
import com.github.enma11235.generic.poll.system.dto.response.GetSurveyResponseBody;
import com.github.enma11235.generic.poll.system.dto.response.GetSurveysResponseBody;
import com.github.enma11235.generic.poll.system.dto.response.VoteResponseBody;
import com.github.enma11235.generic.poll.system.model.Option;
import com.github.enma11235.generic.poll.system.model.Poll;
import com.github.enma11235.generic.poll.system.service.PollService;
import com.github.enma11235.generic.poll.system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/poll")
public class PollController {

    private final PollService pollService;


    @Autowired
    public PollController(PollService pollService, AuthController authController, UserService userService) {
        this.pollService = pollService;
    }

    // CREATE SURVEY
    @PostMapping
    public ResponseEntity<CreateSurveyResponseBody> createSurvey(@RequestBody @Valid CreateSurveyRequestBody body) {
        //obtenemos el token
        String token = body.getToken();
        SurveyDTO survey = pollService.createSurvey(body.getTitle(), body.getOptions(), token);
        CreateSurveyResponseBody responseBody = new CreateSurveyResponseBody(survey.getId(), survey.getTitle(), survey.getCreator(), survey.getOptions(), survey.getCreated_at(), 0);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    // GET SURVEY
    @GetMapping("/{id}")
    public ResponseEntity<GetSurveyResponseBody> getSurveyById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        //obtenemos el token
        String token = authorizationHeader.substring(7);
        SurveyDTO surveyDTO = pollService.getSurveyById(id, token);
        GetSurveyResponseBody responseBody = new GetSurveyResponseBody(surveyDTO.getId(), surveyDTO.getTitle(), surveyDTO.getCreator(), surveyDTO.getOptions(), surveyDTO.getCreated_at());
        return ResponseEntity.ok(responseBody);
    }

    //GET ALL POLLS
    @GetMapping
    public ResponseEntity<GetAllPollsResponseBody> getAllPolls() {
        List<PollData> polls = pollService.getAllPolls();
        GetAllPollsResponseBody responseBody = new GetAllPollsResponseBody(polls);
        return ResponseEntity.ok(responseBody);
    }

    //VOTE
    @PostMapping("/{survey_id}/{option_id}")
    public ResponseEntity<VoteResponseBody> vote(@PathVariable Long survey_id, @PathVariable Long option_id, @RequestBody @Valid VoteRequestBody body) {
        Poll poll = pollService.vote(option_id, body.getToken());
        HashMap<String, Object> creator = new HashMap<String, Object>();
        creator.put("id", poll.getUser().getId());
        creator.put("nickname", poll.getUser().getNickname());
        creator.put("image", poll.getUser().getImg());

        List<HashMap<String, Object>> optionsList = new ArrayList<HashMap<String, Object>>();
        for(Option op : poll.getOptions()) {
            HashMap<String, Object> optionHashMap = new HashMap<String, Object>();
            optionHashMap.put("id", op.getId());
            optionHashMap.put("name", op.getName());
            optionHashMap.put("votes", op.getVotes().size());
            optionsList.add(optionHashMap);
        }

        VoteResponseBody responseBody = new VoteResponseBody(poll.getId(), poll.getTitle(), creator, optionsList, poll.getTotal_votes(), poll.getCreated_at());
        return ResponseEntity.ok(responseBody);
    }
}