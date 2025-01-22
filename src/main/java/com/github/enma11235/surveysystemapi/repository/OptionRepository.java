package com.github.enma11235.surveysystemapi.repository;

import com.github.enma11235.surveysystemapi.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
    // No es necesario definir ni implementar métodos. Spring Data JPA lo hace automáticamente.
}