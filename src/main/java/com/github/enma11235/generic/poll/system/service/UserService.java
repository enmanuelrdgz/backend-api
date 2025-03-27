package com.github.enma11235.generic.poll.system.service;

import com.github.enma11235.generic.poll.system.dto.model.UserData;
import com.github.enma11235.generic.poll.system.exception.AuthException;
import com.github.enma11235.generic.poll.system.exception.NicknameAlreadyInUseException;
import com.github.enma11235.generic.poll.system.exception.UserNotFoundException;
import com.github.enma11235.generic.poll.system.model.User;
import com.github.enma11235.generic.poll.system.utils.JwtUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.github.enma11235.generic.poll.system.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    //GET USER
    public UserData getUserById(Long id, String token) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            boolean validToken = jwtUtils.validateToken(token);
            if (validToken) {
                String nickname = jwtUtils.extractUsername(token);
                if (user.get().getUsername().equals(nickname)) {
                    return new UserData(user.get().getId(), user.get().getUsername());
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

    //CREATE USER
    public User createUser(String nickname, String password) {
        Optional<User> userWithSameNickname = userRepository.findByUsername(nickname);
        if (userWithSameNickname.isPresent()) {
            throw new NicknameAlreadyInUseException("Nickname '" + nickname + "' is already taken.");
        } else {
            LocalDate now = LocalDate.now();

            User user = new User();
            user.setUsername(nickname);
            user.setPassword(password);
            user.setCreated_at(now.toString());
            return userRepository.save(user);
        }
    }

    public long getUserId(String token) {
        boolean validToken = jwtUtils.validateToken(token);
        if (validToken) {
            String nickname = jwtUtils.extractUsername(token);
            Optional<User> user = userRepository.findByUsername(nickname);
            if (user.isPresent()) {
                return user.get().getId();
            } else {
                throw new UserNotFoundException("There is no user with that id");
            }
        }
        return -1;
    }

    public User editUser(String new_nickname, String new_password, String token) {
        boolean validToken = jwtUtils.validateToken(token);
        if (validToken) {
            String nickname = jwtUtils.extractUsername(token);
            Optional<User> user = userRepository.findByUsername(nickname);
            if (user.isPresent()) {
                user.get().setUsername(new_nickname);
                user.get().setPassword(new_password);
                userRepository.save(user.get());
                return user.get();
            } else {
                throw new UserNotFoundException("User does not exist");
            }
        } else {
            throw new AuthException("Invalid Token");
        }
    }

    public boolean doesUserExists(String nickname) {
        Optional<User> user = userRepository.findByUsername(nickname);
        return user.isPresent();
    }

    public Optional<User> getUserByNickname(String nickname) {
        return userRepository.findByUsername(nickname);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
