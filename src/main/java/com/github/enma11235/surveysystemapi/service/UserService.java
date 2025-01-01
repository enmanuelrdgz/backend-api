package com.github.enma11235.surveysystemapi.service;

import com.github.enma11235.surveysystemapi.dto.model.UserDTO;
import com.github.enma11235.surveysystemapi.dto.response.CreateUserResponseBody;
import com.github.enma11235.surveysystemapi.exception.AuthException;
import com.github.enma11235.surveysystemapi.exception.NicknameAlreadyInUseException;
import com.github.enma11235.surveysystemapi.exception.UserNotFoundException;
import com.github.enma11235.surveysystemapi.model.User;
import com.github.enma11235.surveysystemapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import com.github.enma11235.surveysystemapi.repository.UserRepository;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    //GET USER
    public UserDTO getUserById(Long id, String token) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            boolean validToken = jwtTokenProvider.validateToken(token);
            if(validToken) {
                String nickname = jwtTokenProvider.getUsernameFromToken(token);
                if(user.get().getNickname().equals(nickname)) {
                    return new UserDTO(user.get().getId(), user.get().getNickname(), user.get().getCreated_at());
                } else {
                    throw new AuthException("Not authorized to get this user info");
                }
            } else {
                throw new AuthException("Invalid Token");
            }
        } else {
            throw new UserNotFoundException("There is no user with that id");
        }
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    //CREATE USER
    public CreateUserResponseBody createUser(String nickname, String password) {
        Optional<User> userWithSameNickname = userRepository.findByNickname(nickname);
        if(userWithSameNickname.isPresent()) {
            throw new NicknameAlreadyInUseException("Nickname '" + nickname + "' is already taken.");
        } else {
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
            String formattedDate = now.format(DateTimeFormatter.ISO_INSTANT);

            User user = new User();
            user.setNickname(nickname);
            user.setPassword(password);
            user.setCreated_at(formattedDate);
            User savedUser = userRepository.save(user);
            return new CreateUserResponseBody(savedUser.getId(), savedUser.getNickname(), formattedDate);
        }
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }
}
