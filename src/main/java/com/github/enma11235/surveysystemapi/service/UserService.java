package com.github.enma11235.surveysystemapi.service;

import com.github.enma11235.surveysystemapi.exception.AuthException;
import com.github.enma11235.surveysystemapi.dto.response.CreateUserResponseBody;
import com.github.enma11235.surveysystemapi.model.User;
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

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    //CREATE USER
    public CreateUserResponseBody createUser(String nickname, String password) {
        Optional<User> userWithSameNickname = userRepository.findByNickname(nickname);
        if(userWithSameNickname.isPresent()) {
            throw new AuthException("Nickname '" + nickname + "' is already taken.");
        } else {
            User user = new User();
            user.setNickname(nickname);
            user.setPassword(password);
            User savedUser = userRepository.save(user);
            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
            String formattedDate = now.format(DateTimeFormatter.ISO_INSTANT);
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
