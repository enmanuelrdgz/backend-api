package surveys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveys.model.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
}