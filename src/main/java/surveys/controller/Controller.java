package surveys.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import surveys.model.Service;
import surveys.controller.dto.ApiResponseDTO;
import surveys.controller.dto.CreateSurveyDTO;
import surveys.controller.dto.CreateVoteDTO;
import surveys.model.dto.SurveyDTO;
import surveys.model.dto.VoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    private final Service service;

    @Autowired
    public Controller(Service service) {
        this.service = service;
    }

    // este endpoint solo es accesible por mi,
    // y lo uso para crear nuevas encuestas en el sistema
    @PostMapping("/surveys/create")
    public ApiResponseDTO<SurveyDTO> createSurvey(@RequestBody CreateSurveyDTO surveyDto) {
        SurveyDTO createdSurvey = service.createNewSurvey(surveyDto.getTitle(), surveyDto.getOptions());
        return new ApiResponseDTO<SurveyDTO>(
                200,
                "OK",
                createdSurvey
        );
    }

    // este endpoint lo uso para obtener las encuestas activas
    @GetMapping("/surveys/active")
    public ApiResponseDTO<List<SurveyDTO>> getActiveSurveys() {
        List<SurveyDTO> surveys = service.getActiveSurveys();
        return new ApiResponseDTO<List<SurveyDTO>>(
                200,
                "OK",
                surveys
        );
    }

    @PostMapping("/votes/create")
    public ApiResponseDTO<VoteDTO> createVote(HttpServletRequest request, @RequestBody @Valid CreateVoteDTO createVoteDto) {
        long survey_id = createVoteDto.getOption_id();
        long option_id = createVoteDto.getOption_id();
        // Esto se hace para manejar el caso en el que la solicitud ha pasado por un proxy
        // antes de llegar a la application.
        String ip_address = request.getHeader("X-Forwarded-For");
        if (ip_address == null || ip_address.isEmpty() || "unknown".equalsIgnoreCase(ip_address)) {
            ip_address = request.getRemoteAddr();
        }
        VoteDTO voteDTO = service.createNewVote(survey_id, option_id, ip_address);

        return new ApiResponseDTO<VoteDTO>(
                200,
                "Vote created successfully",
                voteDTO
        );
    }

}