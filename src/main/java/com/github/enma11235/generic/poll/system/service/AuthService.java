package com.github.enma11235.generic.poll.system.service;

import com.github.enma11235.generic.poll.system.exception.AuthException;
import com.github.enma11235.generic.poll.system.model.User;
import com.github.enma11235.generic.poll.system.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthService(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    public String signIn(String nickname, String password) {
        // Verificar si el usuario existe
        Optional<User> user = userService.getUserByNickname(nickname);
        if(user.isPresent()) {
            // Verificar las credenciales
            if (!password.equals(user.get().getPassword())) {
                throw new AuthException("Invalid password");
            } else {
                // Generar un token JWT
                return jwtUtils.generateToken(user.get());
            }
        } else {
            throw new AuthException("Invalid nickname");
        }
    }

    public String signUp(String nickname, String password) {
        //verificar que el nickname esta disponible
        boolean isNicknameTaken = userService.doesUserExists(nickname);
        if(!isNicknameTaken) {
            User newUser = userService.createUser(nickname, password);
            return jwtUtils.generateToken(newUser);
        } else {
            throw new AuthException(nickname + " is not available");
        }
    }
}
