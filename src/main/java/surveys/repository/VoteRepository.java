package surveys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveys.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
