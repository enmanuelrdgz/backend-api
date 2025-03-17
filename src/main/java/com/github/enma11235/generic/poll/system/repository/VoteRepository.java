package com.github.enma11235.generic.poll.system.repository;

import com.github.enma11235.generic.poll.system.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
