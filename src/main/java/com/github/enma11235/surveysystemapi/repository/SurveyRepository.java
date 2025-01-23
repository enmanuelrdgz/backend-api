package com.github.enma11235.surveysystemapi.repository;
import com.github.enma11235.surveysystemapi.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // No es necesario definir ni implementar métodos. Spring Data JPA lo hace automáticamente.
}