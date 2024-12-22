package com.portfolio.central_server.controller;

import com.portfolio.central_server.DTO.UserDTO;
import com.portfolio.central_server.model.Survey;
import com.portfolio.central_server.model.User;
import com.portfolio.central_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserDTO> usersListDTO = new ArrayList<UserDTO>();
        for(User u : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(u.getId());
            userDTO.setNickname(u.getNickname());
            usersListDTO.add(userDTO);
        }
        return ResponseEntity.ok(usersListDTO);
    }

    // Endpoint to get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        if(user.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setNickname(user.get().getNickname());
            userDTO.setId(user.get().getId());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}