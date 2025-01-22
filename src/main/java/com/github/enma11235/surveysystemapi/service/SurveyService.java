package com.github.enma11235.surveysystemapi.service;

import com.github.enma11235.surveysystemapi.dto.model.SurveyCreator;
import com.github.enma11235.surveysystemapi.dto.model.SurveyDTO;
import com.github.enma11235.surveysystemapi.dto.model.SurveyOption;
import com.github.enma11235.surveysystemapi.dto.response.GetSurveysResponseBody;
import com.github.enma11235.surveysystemapi.exception.AuthException;
import com.github.enma11235.surveysystemapi.exception.SurveyNotFoundException;
import com.github.enma11235.surveysystemapi.exception.UserNotFoundException;
import com.github.enma11235.surveysystemapi.model.Option;
import com.github.enma11235.surveysystemapi.model.Survey;
import com.github.enma11235.surveysystemapi.model.User;
import com.github.enma11235.surveysystemapi.repository.UserRepository;
import com.github.enma11235.surveysystemapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.github.enma11235.surveysystemapi.repository.SurveyRepository;
import com.github.enma11235.surveysystemapi.utils.DateDifferenceCalculator;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    //GET SURVEY
    public SurveyDTO getSurveyById(Long id, String token) {
        boolean validToken = jwtTokenProvider.validateToken(token);
        if(validToken) {
            String nickname = jwtTokenProvider.getUsernameFromToken(token);
            Optional<Survey> survey = surveyRepository.findById(id);
            if(survey.isPresent()) {
                String creatorNickname = survey.get().getUser().getNickname();
                if(creatorNickname.equals(nickname)) {
                    //creamos las opciones
                    List<SurveyOption> options = new ArrayList<SurveyOption>();
                    for(Option op : survey.get().getOptions()) {
                        options.add(new SurveyOption(op.getId(), op.getName(), op.getVotes().size()));
                    }
                    return new SurveyDTO(
                        survey.get().getId(),
                        survey.get().getTitle(),
                        new SurveyCreator(survey.get().getUser().getId(), survey.get().getUser().getNickname()),
                        options,
                        survey.get().getCreated_at(),
                        survey.get().getTotal_votes()
                    );

                } else {
                    throw new AuthException("You are not the creator of this survey");
                }
            } else {
                throw new SurveyNotFoundException("The survey does not exist");
            }
        } else {
            throw new AuthException("Invalid token");
        }
    }

    //CREATE SURVEY
    public SurveyDTO createSurvey(String title, List<String> options, String token) {
        boolean validToken = jwtTokenProvider.validateToken(token);
        if(validToken) {
            String nickname = jwtTokenProvider.getUsernameFromToken(token);
            Optional<User> user = userRepository.findByNickname(nickname);
            if(user.isPresent()) {
                LocalDate date = LocalDate.now();
                String dateString = date.toString();

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
                survey.setCreated_at(dateString);
                survey.setTotal_votes(0);
                Survey savedSurvey = surveyRepository.save(survey);

                SurveyCreator creator = new SurveyCreator(user.get().getId(), user.get().getNickname());
                List<SurveyOption> savedSurveyOptions = new ArrayList<SurveyOption>();
                for(Option op : savedSurvey.getOptions()) {
                    SurveyOption sop = new SurveyOption(op.getId(), op.getName(), 0);
                    savedSurveyOptions.add(sop);
                }
                return new SurveyDTO(savedSurvey.getId(), savedSurvey.getTitle(), creator, savedSurveyOptions, savedSurvey.getCreated_at(), 0);
            } else {
                throw new UserNotFoundException("It seems like your user was deleted");
            }
        } else {
            throw new AuthException("Invalid token, are you logged in?");
        }
    }

    //GET ALL SURVEYS
    public List<GetSurveysResponseBody> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();

        List<GetSurveysResponseBody> returnList = new ArrayList<GetSurveysResponseBody>();

        for(Survey s : surveys) {
            //debemos sacar los usuarios y las opciones de cada survey
            HashMap<String, Object> creator = new HashMap<String, Object>();
            creator.put("id", s.getUser().getId());
            creator.put("nickname", s.getUser().getNickname());
            creator.put("image", s.getUser().getImg());

            List<HashMap<String, Object>> optionsList = new ArrayList<HashMap<String, Object>>();
            for(Option op : s.getOptions()) {
                HashMap<String, Object> optionHashMap = new HashMap<String, Object>();
                optionHashMap.put("id", op.getId());
                optionHashMap.put("name", op.getName());
                optionHashMap.put("votes", op.getVotes().size());
                optionsList.add(optionHashMap);
            }
            long date = DateDifferenceCalculator.calcularDiferenciaDias(s.getCreated_at(), LocalDate.now().toString());
            String dateString;
            if(date == 0) {
                dateString = "today";
            } else {
                dateString = date + " days ago";
            }
            returnList.add(new GetSurveysResponseBody(s.getId(), s.getTitle(), creator, optionsList, s.getTotal_votes(), dateString));

        }
        return returnList;
    }
}
