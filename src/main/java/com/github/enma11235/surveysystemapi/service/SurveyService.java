package com.github.enma11235.surveysystemapi.service;

import com.github.enma11235.surveysystemapi.dto.model.SurveyCreator;
import com.github.enma11235.surveysystemapi.dto.model.SurveyDTO;
import com.github.enma11235.surveysystemapi.dto.model.SurveyOption;
import com.github.enma11235.surveysystemapi.dto.response.CreateUserResponseBody;
import com.github.enma11235.surveysystemapi.exception.AuthException;
import com.github.enma11235.surveysystemapi.exception.UserNotFoundException;
import com.github.enma11235.surveysystemapi.model.Option;
import com.github.enma11235.surveysystemapi.model.Survey;
import com.github.enma11235.surveysystemapi.model.User;
import com.github.enma11235.surveysystemapi.repository.UserRepository;
import com.github.enma11235.surveysystemapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.github.enma11235.surveysystemapi.repository.SurveyRepository;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }

    //method to find a survey by ID
    public Optional<Survey> findSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    //method to get all surveys
    public List<Survey> findAllSurveys(){
        return surveyRepository.findAll();
    }

    //CREATE SURVEY
    public SurveyDTO createSurvey(String title, List<String> options, String token) {
        boolean validToken = jwtTokenProvider.validateToken(token);
        if(validToken) {
            String nickname = jwtTokenProvider.getUsernameFromToken(token);
            Optional<User> user = userRepository.findByNickname(nickname);
            if(user.isPresent()) {
                ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
                String formattedDate = now.format(DateTimeFormatter.ISO_INSTANT);

                Survey survey = new Survey();
                survey.setTitle(title);
                survey.setUser(user.get());
                List<Option> surveyOptions = new ArrayList<Option>();
                for(String name : options) {
                    Option op = new Option();
                    op.setSurvey(survey);
                    op.setName(name);
                    surveyOptions.add(op);
                }
                survey.setOptions(surveyOptions);
                survey.setCreated_at(formattedDate);
                Survey savedSurvey = surveyRepository.save(survey);

                SurveyCreator creator = new SurveyCreator(user.get().getId(), user.get().getNickname());
                List<SurveyOption> savedSurveyOptions = new ArrayList<SurveyOption>();
                for(Option op : savedSurvey.getOptions()) {
                    SurveyOption sop = new SurveyOption(op.getId(), op.getName(), 0);
                    savedSurveyOptions.add(sop);
                }
                return new SurveyDTO(savedSurvey.getId(), savedSurvey.getTitle(), creator, savedSurveyOptions, savedSurvey.getCreated_at());
            } else {
                throw new UserNotFoundException("It seems like your user was deleted");
            }
        } else {
            throw new AuthException("Invalid token, are you logged in?");
        }
    }

    public void deleteSurveyById(Long id) {
        surveyRepository.deleteById(id);
    }
}
