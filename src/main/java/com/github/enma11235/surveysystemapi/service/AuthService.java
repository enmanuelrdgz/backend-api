package com.github.enma11235.surveysystemapi.service;

import com.github.enma11235.surveysystemapi.exception.AuthException;
import com.github.enma11235.surveysystemapi.model.User;
import com.github.enma11235.surveysystemapi.repository.UserRepository;
import com.github.enma11235.surveysystemapi.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String authenticate(String nickname, String password) {
        // Verificar si el usuario existe
        Optional<User> user = userRepository.findByNickname(nickname);
        if(user.isPresent()) {
            // Verificar las credenciales
            if (!password.equals(user.get().getPassword())) {
                throw new AuthException("Invalid password");
            }
            // Generar un token JWT
            return jwtTokenProvider.generateToken(user.get());
        } else {
            throw new AuthException("Invalid nickname");
        }
    }
}
