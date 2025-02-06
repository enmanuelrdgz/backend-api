package com.github.enma11235.generic.poll.system.service;

import com.github.enma11235.generic.poll.system.exception.AuthException;
import com.github.enma11235.generic.poll.system.model.User;
import com.github.enma11235.generic.poll.system.repository.UserRepository;
import com.github.enma11235.generic.poll.system.security.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    public String authenticate(String nickname, String password) {
        // Verificar si el usuario existe
        Optional<User> user = userRepository.findByNickname(nickname);
        if(user.isPresent()) {
            // Verificar las credenciales
            if (!password.equals(user.get().getPassword())) {
                throw new AuthException("Invalid password");
            } else {
                // Generar un token JWT
                return jwtTokenProvider.generateToken(user.get());
            }
        } else {
            throw new AuthException("Invalid nickname");
        }
    }

    public String signup(String nickname, String password) {
        //verificar que el nickname esta disponible
        Optional<User> user = userRepository.findByNickname(nickname);
        if(user.isEmpty()) {
            User newUser = userService.createUser(nickname, password);
            //autenticamos al nuevo usuario
            return authenticate(newUser.getNickname(), newUser.getPassword());
        } else {
            throw new AuthException("Nickname is not available");
        }
    }
}
