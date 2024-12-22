package com.portfolio.central_server.repository;

import com.portfolio.central_server.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // No es necesario definir ni implementar métodos. Spring Data JPA lo hace automáticamente.
}