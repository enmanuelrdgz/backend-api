package surveys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import surveys.dto.VoteDTO;
import surveys.model.Vote;
import surveys.repository.VoteRepository;
import surveys.utils.Mapper;

@Component
public class VoteService {
    private final VoteRepository voteRepository;
    private final Mapper mapper;

    @Autowired
    public VoteService(VoteRepository voteRepository, Mapper mapper) {
        this.voteRepository = voteRepository;
        this.mapper = mapper;
    }

    public VoteDTO createNewVote(VoteDTO voteDTO) {

        Vote vote = mapper.toVoteEntity(voteDTO);
        Vote savedVote = voteRepository.save(vote); // Esto puede lanzar una DataIntegrityViolationException si
        return mapper.toVoteDTO(savedVote);         // el usuario ya ha votado en la encuesta

    }

}
