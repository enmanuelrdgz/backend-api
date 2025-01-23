package com.github.enma11235.surveysystemapi.repository;

import com.github.enma11235.surveysystemapi.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
