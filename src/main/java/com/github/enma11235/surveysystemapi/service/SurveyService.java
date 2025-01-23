package com.github.enma11235.surveysystemapi.service;

import com.github.enma11235.surveysystemapi.dto.model.*;
import com.github.enma11235.surveysystemapi.dto.response.*;
import com.github.enma11235.surveysystemapi.exception.*;
import com.github.enma11235.surveysystemapi.model.*;
import com.github.enma11235.surveysystemapi.repository.*;
import com.github.enma11235.surveysystemapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.github.enma11235.surveysystemapi.utils.DateDifferenceCalculator;

import java.time.LocalDate;
import java.util.*;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final OptionRepository optionRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public SurveyService(SurveyRepository surveyRepository, OptionRepository optionRepository, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, VoteRepository voteRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
        this.optionRepository = optionRepository;
        this.voteRepository = voteRepository;
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

    //ADD VOTE
    public Survey vote(long option_id, String token) {
        boolean validToken = jwtTokenProvider.validateToken(token);
        if(validToken) {
            String nickname = jwtTokenProvider.getUsernameFromToken(token);
            Optional<User> user = userRepository.findByNickname(nickname);
            Optional<Option> option = optionRepository.findById(option_id);
            if(option.isPresent() && user.isPresent()) {
                Survey survey = option.get().getSurvey();
                //se verifica que el usuario no ha votado antes alguna opcion de la misma encuesta
                boolean userAlreadyVoteAnOption = false;
                List<Option> survey_options = survey.getOptions();
                Option optWithVoteToRemove = null;
                Vote voteToRemove = null;
                for(Option opt : survey_options) {
                    List<Vote> opt_votes = opt.getVotes();
                    for(Vote vote : opt_votes) {
                        if(vote.getUser().getNickname().equals(nickname)) {
                            userAlreadyVoteAnOption = true;
                            optWithVoteToRemove = opt;
                            voteToRemove = vote;
                        }
                    }
                }
                if(userAlreadyVoteAnOption) {
                    optWithVoteToRemove.getVotes().remove(voteToRemove); //intuitivamente, si elimino el voto de la lista de votos de la opcion, spring deberia eliminar el voto automaticamente
                    optionRepository.save(optWithVoteToRemove);
                    //como spring ha eliminado el voto, necesito crear uno nuevo
                    Vote newVote = new Vote(user.get(), option.get());
                    option.get().getVotes().add(newVote);
                    voteRepository.save(newVote);
                    optionRepository.save(option.get());
                    return surveyRepository.save(survey);
                } else {
                    //como spring ha eliminado el voto, necesito crear uno nuevo
                    Vote newVote = new Vote(user.get(), option.get());
                    option.get().getVotes().add(newVote);
                    survey.setTotal_votes(survey.getTotal_votes() + 1);
                    voteRepository.save(newVote);
                    optionRepository.save(option.get());
                    return surveyRepository.save(survey);
                }
            } else {
                throw new SurveyNotFoundException("The survey does not exist");
            }
        } else {
            throw new AuthException("Invalid token");
        }
    }
}
