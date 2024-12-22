package com.portfolio.central_server.repository;

import com.portfolio.central_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // No es necesario definir ni implementar métodos. Spring Data JPA lo hace automáticamente.
}