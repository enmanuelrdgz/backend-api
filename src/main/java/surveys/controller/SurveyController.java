package surveys.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import surveys.service.SurveyService;
import surveys.dto.ApiResponseDTO;
import surveys.dto.SurveyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<SurveyDTO>> createSurvey(@RequestBody SurveyDTO surveyDto) {
        SurveyDTO createdSurvey = surveyService.createNewSurvey(surveyDto);
        return new ResponseEntity<>(
                new ApiResponseDTO<SurveyDTO>(201, "CREATED", createdSurvey),
                HttpStatus.CREATED
        );
    }

    // este endpoint lo uso para obtener las encuestas activas
    @GetMapping("/active")
    public ApiResponseDTO<List<SurveyDTO>> getActiveSurveys() {
        List<SurveyDTO> surveyList = surveyService.getActiveSurveys();
        return new ApiResponseDTO<>(
                200,
                "OK",
                surveyList
        );
    }
}