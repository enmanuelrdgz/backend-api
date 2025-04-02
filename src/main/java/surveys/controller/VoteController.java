package surveys.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import surveys.dto.ApiResponseDTO;
import surveys.dto.VoteDTO;
import surveys.service.VoteService;

@RestController
@RequestMapping("/votes")
public class VoteController {
    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<VoteDTO>> createVote(HttpServletRequest request, @RequestBody @Valid VoteDTO voteDto) {
        // Esta es una medida de seguridad
        voteDto.setId(null);
        voteDto.setIpAddress(null);

        // Esto se hace para manejar el caso en el que la solicitud ha pasado por un proxy
        // antes de llegar a la application.
        voteDto.setIpAddress(request.getHeader("X-Forwarded-For"));
        if (voteDto.getIpAddress() == null || voteDto.getIpAddress().isEmpty() || "unknown".equalsIgnoreCase(voteDto.getIpAddress())) {
            voteDto.setIpAddress(request.getRemoteAddr());
        }

        VoteDTO savedVoteDTO = voteService.createNewVote(voteDto);

        return new ResponseEntity<>(
                new ApiResponseDTO<VoteDTO>(200,"Vote created successfully", savedVoteDTO),
                HttpStatus.CREATED
        );
    }
}
